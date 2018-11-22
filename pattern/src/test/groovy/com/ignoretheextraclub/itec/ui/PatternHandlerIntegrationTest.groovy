package com.ignoretheextraclub.itec.ui

import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType
import spock.lang.Specification
import spock.lang.Unroll

class PatternHandlerIntegrationTest extends Specification {

	def handler = new PatternHandler();

	def "should handle FHS 975"() {
		given:
		def request = new PatternRequest("975",
				SiteswapType.FOUR_HANDED_SITESWAP,
				SortType.FOUR_HANDED_PASSING,
				Form.REDUCED)

		when:
		def response = handler.handleRequest(request, null)

		then:
		response.getErrorMessages().isEmpty()

		with(response) {
			statusCode == 200
			errorMessages.isEmpty()
		}

		with(response.getPattern()) {
			title == "597"
			type == "Four Handed Siteswap"
			numJugglers == 2
			numHands == 4
			numObjects == 7
			prime == true
			grounded == true
			siteswap == "597"
			names == ["597"] as Set
			shortDescriptions[Locale.ENGLISH].length() < 140
		}
	}

	def "should handle missing siteswap"() {
		given:
		def request = new PatternRequest()

		when:
		def response = handler.handleRequest(request, null);

		then:
		with(response) {
			statusCode == 400
			pattern == null
		}
	}

	def "should handle no type specified"() {
		given:
		def request = PatternRequest.builder()
				.siteswap("534")
				.build()

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			statusCode == 200
			errorMessages.isEmpty()
		}

		with(response.getPattern()) {
			title == "534"
			type == "Two Handed Siteswap"
			numJugglers == 1
			numHands == 2
			numObjects == 4
			prime == false
			grounded == true
			siteswap == "534"
			names == ["534"] as Set
		}
	}

	def "should handle invalid siteswap"() {
		given:
		def request = PatternRequest.builder()
				.siteswap("344")
				.build();

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			statusCode == 400
			errorMessages.size() == 1
		}
	}

	def "should respect original form"() {
		given:
		def request = PatternRequest.builder()
				.siteswap("534534")
				.form(Form.ORIGINAL)
				.build();

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			statusCode == 200
			errorMessages.isEmpty()
		}


		with(response.getPattern()) {
			title == "534534"
			type == "Two Handed Siteswap"
			numJugglers == 1
			numHands == 2
			numObjects == 4
			prime == false
			grounded == true
			siteswap == "534534"
			names == ["534534"] as Set
		}
	}

	def "should reduce by default"() {
		given:
		def request = PatternRequest.builder()
				.siteswap("534534")
				.build();

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			statusCode == 200
			errorMessages.isEmpty()
		}

		with(response.getPattern()) {
			title == "534"
			type == "Two Handed Siteswap"
			numJugglers == 1
			numHands == 2
			numObjects == 4
			prime == false
			grounded == true
			siteswap == "534"
			names == ["534"] as Set
		}
	}

	@Unroll
	def "should handle #siteswap #type"(String siteswap, SiteswapType type) {
		given:
		def request = PatternRequest.builder()
				.siteswap(siteswap)
				.type(type)
				.build();

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			statusCode == 200
			errorMessages.isEmpty()
		}

		where:
		siteswap       | type
		"50505"        | SiteswapType.TWO_HANDED_SITESWAP
		"(4,4)(4x,4x)" | SiteswapType.TWO_HANDED_SITESWAP
		"77786"        | SiteswapType.FOUR_HANDED_SITESWAP
		"<3p|3p><3|3>" | SiteswapType.PASSING_SITESWAP
	}

	@Unroll
	def "should sort #siteswap using #sort to #expected"(String siteswap, SortType sort, String expected) {
		given:
		def request = PatternRequest.builder()
				.siteswap(siteswap)
				.type(SiteswapType.FOUR_HANDED_SITESWAP)
				.sort(sort)
				.build();

		when:
		def response = handler.handleRequest(request, null)

		then:
		with(response) {
			statusCode == 200
			errorMessages.isEmpty()
		}

		response.getPattern().getSiteswap() == expected

		where:
		siteswap | sort                         | expected
		"50505"  | SortType.HIGHEST_THROW_FIRST | "55050"
		"50505"  | SortType.NO_SORT             | "50505"
		"678"    | SortType.HIGHEST_THROW_FIRST | "867"
		"678"    | SortType.NO_SORT             | "678"

	}

}
