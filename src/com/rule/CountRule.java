package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.ConnectStereotypeState;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.rule.base.BaseRule;
import com.rule.base.ComplicatedRule;

/**
 * More like the combination ClosureRule and CombinationRule;
 */
public class CountRule extends ComplicatedRule {

	public BaseRule rule;
	public int least, most;

	public CountRule(BaseRule rule, int least, int most) {
		this.rule = rule;
		this.least = least;
		this.most = most;
	}

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
		return rule.getRuleString() + "{" + least + ", " + most + "}";
	}
}
