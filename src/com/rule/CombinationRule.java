package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.ConnectStereotypeState;
import com.diagram.stereotype.algorithm.NFA;
import com.diagram.stereotype.algorithm.utils.DiagramSequence;
import com.rule.base.BaseRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合规则，多条规则的集合，此规则和计数规则为特例规则（匹配方式转变）
 */
public class CombinationRule extends BaseRule {

	private List<BaseRule> rules = new ArrayList<>();

	@Override
	@Deprecated
	public boolean match(char matchCharacter) {
		return false;
	}

	public boolean match(String pattern) {
		BaseStereotypeDiagram diagram = generateDiagram();
		DiagramSequence.adjustSequence(0, diagram.getStart());
		return NFA.accept(pattern, diagram.getStart());
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
