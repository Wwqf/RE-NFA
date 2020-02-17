package com.diagram.stereotype;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.rule.CharacterRule;
import com.rule.OrRule;
import com.rule.base.BaseRule;

import java.io.*;

public class OrStereotypeState extends BaseStereotypeDiagram {

	public OrStereotypeState(OrRule orRule) {
		System.out.println("Handle OrRule by OrStereotypeState - " + orRule.getRuleString() + "");
		solveOrRule(orRule);
	}

	private void solveOrRule(OrRule orRule) {
		if (orRule.getRules().size() == 1) {

			BaseStereotypeDiagram diagram = orRule.getRules().get(0).generateDiagram();
			start = diagram.start;
			end = diagram.end;

			return ;
		}

		for (BaseRule rule : orRule.getRules()) {
			BaseStereotypeDiagram diagram = rule.generateDiagram();
			start.addConvertFunc(new CharacterRule(), diagram.start);
			diagram.end.addConvertFunc(new CharacterRule(), end);
		}
	}
}
