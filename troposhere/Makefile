itec-stack.yaml: itec-stack.py
	python itec-stack.py > itec-stack.yaml

change-set.json: itec-stack.yaml
	aws cloudformation create-change-set \
	--stack-name itec-stack \
	--template-body file://itec-stack.yaml \
	--change-set-name $(csn) \
	--capabilities CAPABILITY_NAMED_IAM \
	--change-set-type UPDATE

	aws cloudformation wait change-set-create-complete \
	--stack-name itec-stack \
	--change-set-name $(csn)

	aws cloudformation describe-change-set \
	--stack-name itec-stack \
	--change-set-name $(csn) > 	itec-stack-change-set.json

	cat itec-stack-change-set.json | jq

execute-change-set: change-set.json
	aws cloudformation execute-change-set \
	--stack-name itec-stack \
	--change-set-name $(csn)

	touch execute-change-set