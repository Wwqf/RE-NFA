package com.diagram.stereotype.algorithm.pattern;

import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.utils.Production;
import com.rule.base.BaseRule;

/**
 * 规则策略, 在Thompson算法中，根据字符选择不同的策略，然后返回该策略构建的有限状态自动机。
 */
public interface RuleStrategy {
	FiniteAutomata construct(Production production);
}
