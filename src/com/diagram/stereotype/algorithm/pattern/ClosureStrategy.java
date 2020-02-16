package com.diagram.stereotype.algorithm.pattern;

import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.utils.Production;
import com.diagram.stereotype.closure.ClosureAttrType;
import com.rule.ClosureRule;

import javax.accessibility.AccessibilityProvider;

public class ClosureStrategy implements RuleStrategy {

	private FiniteAutomata preAutomata;
	public ClosureStrategy(FiniteAutomata preAutomata) {
		this.preAutomata = preAutomata;
	}

	@Override
	public FiniteAutomata construct(Production production) {
		// 例如 [a-zA-Z] 这句正则表达式, 如果检查末尾, 可能会下标溢出, 所以用try ... catch ...

		try {
			if (production.getChar() == '+') {
				production.offset(1);
				return new FiniteAutomata(
						new ClosureRule(preAutomata.rule, ClosureAttrType.POSITIVE_CLOSURE)
				);
			} else if (production.getChar() == '*') {
				production.offset(1);
				return new FiniteAutomata(
						new ClosureRule(preAutomata.rule, ClosureAttrType.KLEENE_CLOSURE)
				);
			} else if (production.getChar() == '?') {
				production.offset(1);
				return new FiniteAutomata(
						new ClosureRule(preAutomata.rule, ClosureAttrType.ZERO_ONE_CLOSURE)
				);
			}
		} catch (Exception ignored) {
		}

		return preAutomata;
	}
}
