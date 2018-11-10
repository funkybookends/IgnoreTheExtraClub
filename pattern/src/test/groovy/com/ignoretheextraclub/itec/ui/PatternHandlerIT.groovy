package com.ignoretheextraclub.itec.ui

import spock.lang.Specification

class PatternHandlerIntegrationTest extends Specification {

	def handler = new PatternHandler();

	def "should handle FHS 975"(String inputType) {
		given:
		def request = new PatternRequest("975", inputType)

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			title == "975"
			type == "Four Handed Siteswap"
			numJugglers == 2
			numHands == 4
			numObjects == 7
			prime == true
			grounded == true
			siteswap == "975"
			names == ["975"] as Set
			shortDescriptions[Locale.ENGLISH].length() < 140
		}

		where:
		inputType | _
		"fhs" | _
		"FHS" | _
		"Four Handed Siteswap" | _
		"FourHandedSiteswap" | _
	}
}
