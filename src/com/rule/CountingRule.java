package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.ConnectStereotypeState;
import com.diagram.stereotype.algorithm.NFA;
import com.diagram.stereotype.algorithm.utils.DiagramSequence;
import com.rule.base.BaseRule;
import com.rule.base.ComplicatedRule;

/**
 * More like the combination ClosureRule and CombinationRule;
 * 计数规则的生成有限状态自动机还未完成
 * {1, 6}
 */
public class CountingRule extends ComplicatedRule {

	public BaseRule rule;
	public int least, most;

	public CountingRule(BaseRule rule, int least, int most) {
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
		DiagramSequence.adjustSequence(0, diagram.getStart());
		return NFA.accept(pattern, diagram.getStart());
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
