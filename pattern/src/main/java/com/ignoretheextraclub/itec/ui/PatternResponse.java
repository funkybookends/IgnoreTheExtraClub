package com.ignoretheextraclub.itec.ui;

import java.util.List;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class PatternResponse
{
	Pattern pattern;
	PatternRequest request;
	Integer statusCode;
	@Singular List<String> errorMessages;
}
