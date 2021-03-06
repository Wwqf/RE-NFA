package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.ConnectStereotypeState;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.rule.base.BaseRule;

import java.util.ArrayList;
import java.util.List;

public class CombinationRule extends BaseRule {

	private List<BaseRule> rules = new ArrayList<>();

	@Override
	@Deprecated
	public boolean match(char matchCharacter) {
		return false;
	}

	public boolean match(String pattern) {
		BaseStereotypeDiagram diagram = generateDiagram();
		StereotypeUtils.adjustSequence(0, diagram.start);
		return StereotypeUtils.accept(pattern, diagram.start);
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		return new ConnectStereotypeState(this);
	}

	@Override
	public String getRuleString() {
		StringBuilder result = new StringBuilder("(");

		for  (BaseRule rule : rules) {
			result.append(rule.getRuleString());
		}
		result.append(")");
		return result.toString();
	}

	public void addRule(BaseRule rule) {
		rules.add(rule);
	}

	public List<BaseRule> getRules() {
		return rules;
	}
}
