from troposphere import GetAtt, Join
from troposphere import Ref, Template, Output
from troposphere.apigateway import ApiKey, StageKey, UsagePlanKey
from troposphere.apigateway import Deployment, Stage, ApiStage
from troposphere.apigateway import Integration, IntegrationResponse
from troposphere.apigateway import Resource, MethodResponse
from troposphere.apigateway import RestApi, Method
from troposphere.apigateway import UsagePlan, QuotaSettings, ThrottleSettings
from troposphere.awslambda import Function, Code
from troposphere.iam import Role, Policy

itec_stack = Template()

## ITEC Stack
apiName = "ITECApi"
lambda_execution_role = "LambdaExecutionRole"
bucket_name = "itec-lambda-function-artifacts"
stage_name = 'v1'

# Create the Api Gateway
itec_rest_api = itec_stack.add_resource(RestApi(apiName, Name = apiName))

# Create a role for the lambda function
itec_stack.add_resource(Role(
    lambda_execution_role,
    Path = "/",
    Policies = [Policy(PolicyName = "root", PolicyDocument = {
            "Version": "2012-10-17",
            "Statement": [{
                    "Action": ["logs:*"],
                    "Resource": "arn:aws:logs:*:*:*",
                    "Effect": "Allow"
                    }, {
                    "Action": ["lambda:*"],
                    "Resource": "*",
                    "Effect": "Allow"
                    }]
            })],
    AssumeRolePolicyDocument = {"Version": "2012-10-17", "Statement": [{
            "Action": ["sts:AssumeRole"],
            "Effect": "Allow",
            "Principal": {
                    "Service": [
                            "lambda.amazonaws.com",
                            "apigateway.amazonaws.com"
                            ]
                    }
            }]},
    ))


def create_frm(stack, prefix, handler):
    function_name = "%sFunction" % prefix
    resource_name = "%sResource" % prefix

    function = stack.add_resource(Function(
        function_name,
        Code = Code(
            S3Bucket = bucket_name,
            S3Key = prefix + "-latest.zip"
            ),
        Handler = handler,
        Role = GetAtt(lambda_execution_role, "Arn"),
        Runtime = "java8",
        ))

    # Create a resource to map the lambda function to
    resource = stack.add_resource(Resource(
        resource_name,
        RestApiId = Ref(itec_rest_api),
        PathPart = prefix,
        ParentId = GetAtt(apiName, "RootResourceId"),
        ))

    # Create a Lambda API method for the Lambda resource
    method = stack.add_resource(Method(
        "%sLambdaMethod" % prefix,
        ApiKeyRequired = True,
        DependsOn = function_name,
        RestApiId = Ref(itec_rest_api),
        AuthorizationType = "NONE",
        ResourceId = Ref(resource),
        HttpMethod = "POST",
        Integration = Integration(
            Credentials = GetAtt(lambda_execution_role, "Arn"),
            Type = "AWS",
            IntegrationHttpMethod = 'POST',
            IntegrationResponses = [
                    IntegrationResponse(
                        StatusCode = '200'
                        ),
                    IntegrationResponse(
                        StatusCode = '400',
                        SelectionPattern = '"statusCode":"400"'
                        ),
                    IntegrationResponse(
                        StatusCode = '500',
                        SelectionPattern = '"statusCode":"500"'
                        )
                    ],
            Uri = Join("", [
                    "arn:aws:apigateway:us-west-2:lambda:path/2015-03-31/functions/",
                    GetAtt(function_name, "Arn"),
                    "/invocations"
                    ])
            ),
        MethodResponses = [
                MethodResponse(
                    "CatResponse",
                    StatusCode = '200'
                    )
                ]
        ))
    return function, resource, method


pattern_function_params = {
        "prefix": "pattern",
        "handler": "com.ignoretheextraclub.itec.ui.PatternHandler::handleRequest"
        }

pattern_function, pattern_resource, pattern_method = create_frm(itec_stack, **pattern_function_params)

# Create a deployment
deployment = itec_stack.add_resource(Deployment(
    "%sDeployment" % stage_name,
    DependsOn = pattern_method,
    RestApiId = Ref(itec_rest_api),
    ))

stage = itec_stack.add_resource(Stage(
    '%sStage' % stage_name,
    StageName = stage_name,
    RestApiId = Ref(itec_rest_api),
    DeploymentId = Ref(deployment)
    ))

api_key_name = "CasparsApiKey"

api_key = itec_stack.add_resource(ApiKey(
    api_key_name,
    Name = api_key_name,
    StageKeys = [StageKey(RestApiId = Ref(itec_rest_api), StageName = Ref(stage))])
    )

# Create an API usage plan
xl_usage_plan_name = "XLUsagePlan"

usage_plan = itec_stack.add_resource(UsagePlan(
    xl_usage_plan_name,
    UsagePlanName = xl_usage_plan_name,
    Description = "A large plan that should suit anyone",
    Quota = QuotaSettings(Limit = 50000, Period = "MONTH"),
    Throttle = ThrottleSettings(BurstLimit = 500, RateLimit = 5000),
    ApiStages = [ApiStage(ApiId = Ref(itec_rest_api), Stage = Ref(stage))])
    )

# tie the usage plan and key together
usage_plan_key = itec_stack.add_resource(UsagePlanKey(
    "%sKey" % xl_usage_plan_name,
    KeyId = Ref(api_key),
    KeyType = "API_KEY",
    UsagePlanId = Ref(usage_plan)
    ))

# Add the deployment endpoint as an output
itec_stack.add_output([
        Output(
            "ApiEndpoint",
            Value = Join("", ["https://", Ref(itec_rest_api), ".execute-api.us-west-2.amazonaws.com/", stage_name]),
            Description = "Endpoint for this stage of the api"),
        Output(
            "ApiKey",
            Value = Ref(api_key),
            Description = "API key"),
        ])

print(itec_stack.to_yaml())
