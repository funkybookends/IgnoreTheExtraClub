build-ui:
	ng build

deploy-ui: build-ui
	aws s3 cp --recursive ./dist/ui/ s3://itec-public-static-content/

clean-deploy-ui: build-ui
	aws s3 rm --recursive s3://itec-public-static-content/
	make deploy-ui

.PHONY: deploy-ui build-ui clean-deploy-ui
