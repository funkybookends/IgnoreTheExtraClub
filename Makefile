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

.PHONY: deploy-pattern-lambda deploy-api