deploy-causaldiagram-lambda:
	./gradlew clean build

	aws s3 cp ./causaldiagram/build/distributions/causaldiagram-latest.zip s3://itec-lambda-function-artifacts/

	aws lambda update-function-code \
	--function-name itec-stack-causaldiagramFunction-AD3EZ1RKX5HW \
	--s3-bucket itec-lambda-function-artifacts \
	--s3-key causaldiagram-latest.zip

deploy-pattern-lambda:
	./gradlew clean build

	aws s3 cp ./pattern/build/distributions/pattern-latest.zip s3://itec-lambda-function-artifacts/

	aws lambda update-function-code \
	--function-name itec-stack-patternFunction-I9HSG97D33OO \
	--s3-bucket itec-lambda-function-artifacts \
	--s3-key pattern-latest.zip

enable-api-key:
	aws apigateway update-api-key \
	--api-key f1jqs3n2zi \
	--patch-operations op='replace',path='/enabled',value='true'

deploy-api:
	aws apigateway create-deployment \
	--rest-api-id jluasn7qrb \
	--stage-name v1

test-pattern-api:
	curl "https://jluasn7qrb.execute-api.us-west-2.amazonaws.com/v1/pattern/" \
	-H "Content-Type: application/json" \
	-H "x-api-key: $(AWS_API_SECRET)" \
	-X POST \
	-d '{"siteswap":"6789A", "type":"FOUR_HANDED_SITESWAP", "sort":"FOUR_HANDED_PASSING"}' \
	| jq

test-causal-diagram-api:
	curl -vvv "https://jluasn7qrb.execute-api.us-west-2.amazonaws.com/v1/causaldiagram/" \
	-H "Content-Type: application/json" \
	-H "x-api-key: $(AWS_API_SECRET)" \
	-X POST \
	-d '{"siteswap":"777786786", "type":"FOUR_HANDED_SITESWAP"}'

.PHONY: deploy-pattern-lambda deploy-api test-pattern-api test-causal-diagram-api