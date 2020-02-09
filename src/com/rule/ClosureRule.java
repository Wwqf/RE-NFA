package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.KleeneClosureState;
import com.diagram.stereotype.PositiveClosureState;
import com.diagram.stereotype.ZeroOneClosureState;
import com.diagram.stereotype.closure.ClosureAttrType;
import com.rule.base.BaseRule;

/**
 * 闭包规则，包含一个基本规则和一个闭包属性
 */
public class ClosureRule extends BaseRule {

	private BaseRule rule;
	private ClosureAttrType type;

	public ClosureRule(BaseRule rule, ClosureAttrType type) {
		this.rule = rule;
		this.type = type;
	}

	@Override
	public boolean match(char matchCharacter) {
		return rule.match(matchCharacter);
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		if (type == ClosureAttrType.KLEENE_CLOSURE) {
			return new KleeneClosureState(rule);
		} else if (type == ClosureAttrType.POSITIVE_CLOSURE) {
			return new PositiveClosureState(rule);
		}
		return new ZeroOneClosureState(rule);
	}

	@Override
	public String getRuleString() {
		String result = "(";
		if (type == ClosureAttrType.KLEENE_CLOSURE) {
			result += rule.getRuleString();
			result += ")*";
		} else if (type == ClosureAttrType.POSITIVE_CLOSURE) {
			result += rule.getRuleString();
			result += ")+";
		} else if (type == ClosureAttrType.ZERO_ONE_CLOSURE) {
			result += rule.getRuleString();
			result += ")?";
		}
		return result;
	}
}
