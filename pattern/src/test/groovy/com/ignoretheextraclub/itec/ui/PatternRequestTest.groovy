package com.ignoretheextraclub.itec.ui

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class PatternRequestTest extends Specification {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	def "should deserialise"() {
		given:
		def json = '''
			{
				"siteswap": "975",
				"type": "fhs"
			}
		'''

		def expected = new PatternRequest("975", "fhs")

		when:
		def actual = OBJECT_MAPPER.readValue(json, PatternRequest.class)

		then:
		expected == actual
	}
}
