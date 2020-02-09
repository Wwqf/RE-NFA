package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.ConnectStereotypeState;
import com.diagram.stereotype.OrStereotypeState;
import com.rule.base.BaseRule;
import com.rule.base.ComplicatedRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 或规则，匹配规则与组合规则不同，或规则只要匹配其中一条即可，而组合规则需要按添加的规则顺序匹配。
 */
public class OrRule extends ComplicatedRule {

	protected List<BaseRule> rules = new ArrayList<>();
	// 是否为补集字符类
	public boolean isComplemented = false;

	@Override
	public boolean match(char matchCharacter) {
		boolean result = subMatch(matchCharacter);
		if (isComplemented) return !result;
		return result;
	}

	private boolean subMatch(char matchCharacter) {
		for (BaseRule rule : rules) {
			if (rule.match(matchCharacter)) return true;
		}
		return false;
	}

	public void addRule(BaseRule rule) {
		rules.add(rule);
	}

	public List<BaseRule> getRules() {
		return rules;
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		BaseStereotypeDiagram diagram = getGenerateClone();

		if (diagram != null) {
			return diagram;
		} else {
			diagram = new OrStereotypeState(this);
		}

		setGenerateClone(diagram);
		return diagram;
	}

	@Override
	public String getRuleString() {
		StringBuilder result = new StringBuilder("[");
		if (isComplemented) {
			result.append("^");
		}

		for (BaseRule rule : rules) {
			result.append(rule.getRuleString());
		}
		result.append("]");

		return result.toString();
	}
}
