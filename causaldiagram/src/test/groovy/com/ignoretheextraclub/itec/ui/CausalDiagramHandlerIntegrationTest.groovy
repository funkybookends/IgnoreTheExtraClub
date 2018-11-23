package com.ignoretheextraclub.itec.ui


import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand
import spock.lang.Specification

class CausalDiagramHandlerIntegrationTest extends Specification {

	def handler = new CausalDiagramHandler();

	def "should 400 missing siteswap"() {
		given:
		def request = CausalDiagramRequest.builder()
				.type(SiteswapType.FOUR_HANDED_SITESWAP)
				.build();

		when:
		ErrorResponse actual = handler.handleRequest(request, null);

		then:
		with(actual) {
			statusCode == 400
			errorMessages.size() == 1
		}
	}

	def "should 400 missing type"() {
		given:
		def request = CausalDiagramRequest.builder()
				.siteswap("975")
				.build();

		when:
		ErrorResponse actual = handler.handleRequest(request, null);

		then:
		with(actual) {
			actual.getStatusCode() == 400
			errorMessages.size() == 1
		}

	}

	def "should ok 975"() {
		given:
		def request = CausalDiagramRequest.builder()
				.siteswap("975")
				.type(SiteswapType.FOUR_HANDED_SITESWAP)
				.build();

		when:
		String actual = handler.handleRequest(request, null);

		then:
		actual != null
	}

	def "should 400 when invalid throwing order"() {
		given:
		def request = CausalDiagramRequest.builder()
				.siteswap("975")
				.fourHandedSiteswapHandOrder((Hand[]) [Hand.LEFT, Hand.LEFT])
				.build();

		when:
		ErrorResponse actual = handler.handleRequest(request, null);

		then:
		with(actual) {
			statusCode == 400
			errorMessages.size() == 1
		}
	}

	def "should ok when passing siteswap"() {
		given:
		def request = CausalDiagramRequest.builder()
				.siteswap("<3p|3p><3|3>")
				.type(SiteswapType.PASSING_SITESWAP)
				.build();

		when:
		String actual = handler.handleRequest(request, null);

		then:
		actual != null
	}


	def "should 400 on invalid siteswap"(String siteswap, SiteswapType type) {
		given:
		def request = CausalDiagramRequest.builder()
				.siteswap(siteswap)
				.type(type)
				.build();

		when:
		ErrorResponse actual = handler.handleRequest(request, null)

		then:
		with(actual) {
			statusCode == 400
			errorMessages.size() == 1
		}

		where:
		siteswap   | type
		"977"      | SiteswapType.FOUR_HANDED_SITESWAP
		"<3p|3><3" | SiteswapType.PASSING_SITESWAP

	}
}
