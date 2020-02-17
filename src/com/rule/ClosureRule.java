package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.KleeneClosureState;
import com.diagram.stereotype.PositiveClosureState;
import com.diagram.stereotype.ZeroOneClosureState;
import com.rule.base.BaseRule;
import com.rule.enums.ClosureType;

public class ClosureRule extends BaseRule {

	private BaseRule rule;
	private ClosureType type;

	public ClosureRule(BaseRule rule, ClosureType type) {
		this.rule = rule;
		this.type = type;
	}

	@Override
	public boolean match(char matchCharacter) {
		return rule.match(matchCharacter);
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		if (type == ClosureType.KLEENE_CLOSURE) {
			return new KleeneClosureState(rule);
		} else if (type == ClosureType.POSITIVE_CLOSURE) {
			return new PositiveClosureState(rule);
		}
		return new ZeroOneClosureState(rule);
	}

	@Override
	public String getRuleString() {
		String result = "(";
		if (type == ClosureType.KLEENE_CLOSURE) {
			result += rule.getRuleString();
			result += ")*";
		} else if (type == ClosureType.POSITIVE_CLOSURE) {
			result += rule.getRuleString();
			result += ")+";
		} else if (type == ClosureType.ZERO_ONE_CLOSURE) {
			result += rule.getRuleString();
			result += ")?";
		}
		return result;
	}
}
