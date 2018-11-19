deploy-pattern-lambda:
	./gradlew clean build
	aws s3 cp ./pattern/build/distributions/pattern-latest.zip s3://itec-lambda-function-artifacts/

enable-api-key:
	aws apigateway update-api-key \
	--api-key f1jqs3n2zi \
	--patch-operations op='replace',path='/enabled',value='true'

deploy-api:
	aws apigateway create-deployment \
	--rest-api-id jluasn7qrb \
	--stage-name v1

.PHONY: deploy-pattern-lambda deploy-api