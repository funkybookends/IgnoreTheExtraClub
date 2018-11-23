package com.ignoretheextraclub.itec.ui;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LambdaRequest
{
	PatternRequest body;
	@Singular Map<String, String> headers;
}
