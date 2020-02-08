package com.diagram.stereotype.algorithm.pattern;

import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.utils.Production;
import com.rule.CountingRule;
import com.rule.base.BaseRule;

import java.util.Map;

/**
 * 大括号策略(emm不知道怎么命名)
 *  1. {digit} 是一个产生式头部的名称, 在这里意为调用此产生式
 *  2. {1, 6} 范围计数规则（对应类为CountRule）
 */
public class BraceStrategy implements RuleStrategy {

	private Map<String, FiniteAutomata> automataMap;
	private BaseRule preRule;

	public BraceStrategy(Map<String, FiniteAutomata> automataMap, BaseRule preRule) {
		// automataMap 用于 {digit} ; 检测是否有这个产生式头部对应的有限状态自动机
		// preRule 用于 {1, 6} ; 该正则规则写为 [a-z]{1, 6} ... 需要一个前导规则

		this.automataMap = automataMap;
		this.preRule = preRule;
	}

	@Override
	public FiniteAutomata construct(Production production) {
		// 偏移一位
		production.offset(1);

		boolean isDigit = Character.isDigit(production.getChar());
		if (isDigit) {
			return digitStrategy(production);
		} else {
			return letterStrategy(production);
		}
	}

	private FiniteAutomata digitStrategy(Production production) {
		int left = 0, right = 0;

		char c;
		while ((c = production.getChar()) != ',') {
			left = left * 10 + (c - '0');
			production.offset(1);
			production.skipSpaces();
		}

		// 偏移到逗号下一位 ','
		production.offset(1);
		production.skipSpaces();

		while ((c = production.getChar()) != '}') {
			right = right * 10 + (c - '0');
			production.offset(1);
			production.skipSpaces();
		}

		// '}'
		production.offset(1);
		if (preRule != null) {
			return new FiniteAutomata(new CountingRule(preRule, left, right));
		}
		throw new RuntimeException("Counting rule mismatch!");
	}

	private FiniteAutomata letterStrategy(Production production) {
		// production head name
		StringBuilder headName = new StringBuilder();

		char c;
		while ((c = production.getChar()) != '}') {
			headName.append(c);
			production.offset(1);
		}

		// '}'
		production.offset(1);

		FiniteAutomata automata = automataMap.get(headName.toString());
		if (automata == null) {
			throw new RuntimeException("Without this production -> " + headName);
		}

		return automata;
	}
}
