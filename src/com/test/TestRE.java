package com.test;

import com.diagram.stereotype.utils.StereotypeUtils;
import com.diagram.unit.State;
import com.logic.RuleDiagram;
import com.logic.ScanningGrammar;

public class TestRE {
	private static final String projectPath = "/home/yanuas/CompilationPrinciple/FinalFA/src/data/input/";
	public static final String id = "id.i";
	public static final String decimal = "decimal.i";
	public static final String test = "test.i";

	ScanningGrammar grammar;

	public TestRE(String filename) {
		grammar = new ScanningGrammar(projectPath + filename);
		grammar.create();
	}

	public void test(String startStateKey, String pattern) {
		RuleDiagram start = grammar.getRuleDiagram(startStateKey);
//		System.out.println(start.getConvertTableString());
		System.out.println("Test(" + startStateKey + "): " + pattern + " --> " + StereotypeUtils.accept(pattern, start.diagram.start));
	}

	public void test(String startStateKey, String pattern, boolean isTrim) {
		RuleDiagram start = grammar.getRuleDiagram(startStateKey);
//		System.out.println(start.getConvertTableString());
		System.out.println("Test(" + startStateKey + "): " + pattern + " --> " + StereotypeUtils.accept(pattern, start.diagram.start, isTrim));
	}

}
