package com.ignoretheextraclub.itec.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType
import spock.lang.Specification

class PatternRequestTest extends Specification {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	def "should deserialize"() {
		given:
		def json = '''
			{
				"siteswap": "975",
				"type": "FOUR_HANDED_SITESWAP", 
				"sort": "FOUR_HANDED_PASSING", 
				"form": "REDUCED"				
			}
		'''

		def expected = new PatternRequest("975", SiteswapType.FOUR_HANDED_SITESWAP, SortType.FOUR_HANDED_PASSING, Form.REDUCED)

		when:
		def actual = OBJECT_MAPPER.readValue(json, PatternRequest.class)

		then:
		expected == actual
	}
}
