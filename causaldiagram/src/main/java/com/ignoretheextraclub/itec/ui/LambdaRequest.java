package com.ignoretheextraclub.itec.ui;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LambdaRequest
{
	CausalDiagramRequest body;
	Map<String, String> headers;
}
