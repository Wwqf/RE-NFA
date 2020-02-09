package com.diagram.stereotype.algorithm.pattern;

import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.utils.Production;
import com.rule.CharacterRule;
import com.rule.CombinationRule;
import com.rule.OrRule;
import com.rule.StringRule;
import com.rule.base.BaseRule;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
 * 小括号策略，在小括号中可以是字符，可以是大括号策略，可以包含另外一个小括号策略，也可以是一条或策略。
 * (\.{digit}+)?
 * (E[+-]?{digit}+)?
 * ({letter_ul} | {digit})*
 * ([+-]? ({letter_ul} | {digit})?)*
 */
public class ParenthesisStrategy implements RuleStrategy {

	private Map<String, FiniteAutomata> automataMap;
	private BaseRule preRule;

	public ParenthesisStrategy(Map<String, FiniteAutomata> automataMap) {
		this.automataMap = automataMap;
	}

	@Override
	public FiniteAutomata construct(Production production) {
		// '('
		production.offset(1);

		// 括号内是否为或规则
		boolean isOrRule = false;
		// 有限状态自动机队列
		Queue<FiniteAutomata> queueFA = new LinkedList<>();

		StringBuilder temporary = new StringBuilder();
		char c;
		while ((c = production.skipSpaces()) != ')') {

			/*
			 * 1. (     2. {
			 * 3. [     4. ordinary char    5. |
			 */

			if (c == '(' || c == '{' || c == '[') {
				connectStringRule(queueFA, temporary);

				// 这三个要检测是否有闭包属性

				FiniteAutomata automata = null;
				FiniteAutomata closureAutomata = null;

				if (c == '(') {
					automata = construct(production);
				} else if (c == '{') {
					automata = new BraceStrategy(automataMap, preRule).construct(production);
				} else { // '['
					automata = new OrStrategy().construct(production);
				}

				closureAutomata = new ClosureStrategy(automata).construct(production);
				preRule = closureAutomata.rule;
				queueFA.add(closureAutomata);
			} else if (c == '|') {
				connectStringRule(queueFA, temporary);

				isOrRule = true;
				production.offset(1);
			} else {
				if (c == '\\') {
					production.offset(1);
					c = production.getChar();
					temporary.append(c);
				} else temporary.append(c);
				production.offset(1);
			}
		}
		connectStringRule(queueFA, temporary);

		// ')'
		production.offset(1);

		FiniteAutomata result = null;
		if (isOrRule) {
			OrRule orRule = new OrRule();
			while (!queueFA.isEmpty()) {
				orRule.addRule(queueFA.poll().rule);
			}
			result = new FiniteAutomata(orRule);
		} else {
			CombinationRule combinationRule = new CombinationRule();
			while (!queueFA.isEmpty()) {
				combinationRule.addRule(queueFA.poll().rule);
			}
			result = new FiniteAutomata(combinationRule);
		}
		return result;
	}

	private void connectStringRule(Queue<FiniteAutomata> queueFA, StringBuilder temporary) {
		if (temporary.length() > 1) {
			// is strings
			queueFA.add(new FiniteAutomata(new StringRule(temporary.toString())));
		} else if (temporary.length() == 1){
			// is char
			queueFA.add(new FiniteAutomata(new CharacterRule(temporary.charAt(0))));
		}
		temporary.delete(0, temporary.length());
	}
}
