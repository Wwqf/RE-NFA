package com.diagram.stereotype.algorithm;

import com.diagram.stereotype.algorithm.pattern.*;
import com.diagram.stereotype.algorithm.utils.Production;
import com.rule.CombinationRule;
import com.rule.base.BaseRule;

import java.util.Map;

public class Thompson {

	// 产生式头部对应的由此产生式主体构建的有限状态自动机
	private Map<String, FiniteAutomata> automataMap;
	// 产生式头部，产生式体
	private Production production;

	public Thompson(Map<String, FiniteAutomata> automataMap, Production production) {
		this.automataMap = automataMap;
		this.production = production;
	}

	public FiniteAutomata execute() {
		CombinationRule combinationRule = new CombinationRule();
		BaseRule preRule = null;

		char c;
		while (!production.indexIsEnd()) {
			c = production.skipSpaces();

			if (c == '(' || c == '{' || c == '[') {
				// 这三个要检测是否有闭包属性

				FiniteAutomata automata = null;
				FiniteAutomata closureAutomata = null;

				if (c == '(') {
					automata = new ParenthesisStrategy(automataMap).construct(production);
				} else if (c == '{') {
					automata = new BraceStrategy(automataMap, preRule).construct(production);
				} else { // '['
					automata = new OrStrategy().construct(production);
				}

				closureAutomata = new ClosureStrategy(automata).construct(production);
				preRule = closureAutomata.rule;
				combinationRule.addRule(closureAutomata.rule);
			} else {
				FiniteAutomata automata = new CharacterStrategy().construct(production);
				preRule = automata.rule;
				combinationRule.addRule(automata.rule);
			}

		}

		// 防止多嵌套规则
		FiniteAutomata result = null;
		if (combinationRule.getRules().size() == 1) {
			result = new FiniteAutomata(combinationRule.getRules().get(0));
		} else if (combinationRule.getRules().size() > 1) {
			result = new FiniteAutomata(combinationRule);
		} else {
			throw new RuntimeException("Finite automata can't be built.");
		}

		return result;
	}
}
