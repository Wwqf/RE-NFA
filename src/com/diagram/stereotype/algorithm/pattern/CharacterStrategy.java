package com.diagram.stereotype.algorithm.pattern;

import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.utils.Escape;
import com.diagram.stereotype.algorithm.utils.Production;
import com.rule.CharacterRule;
import com.rule.StringRule;

/**
 * 字符规则策略, 当遇到任意字符时都可调用此策略
 */
public class CharacterStrategy implements RuleStrategy {

	// 可追加特殊字符
	private static char[] canAppendSC = {
			'\\', '"'
	};

	// 不可追加特殊字符
	private static char[] cantAppendSC = {
			'{', '[', '('
	};

	@Override
	public FiniteAutomata construct(Production production) {
		StringBuilder appendStr = new StringBuilder();

		boolean isRunning = true;
		FiniteAutomata automata = null;

		char c;
		while (!production.indexIsEnd() && isRunning) {
			c = production.getChar();

			for (char item : cantAppendSC) {
				if (c == item) {
					// 中断调用
					isRunning = false;
				}
			}

			if (isRunning) {

				switch (c) {
					case '\\':
						appendStr.append(Escape.getEscape(production));
						break;
					case '"':
						production.offset(1);
						while ((c = production.getChar()) != '"') {
							appendStr.append(c);
							production.offset(1);
						}
						production.offset(1);
						break;
					default:
						appendStr.append(c);
						production.offset(1);
				}
			}
		}

		if (appendStr.length() == 1) {
			automata = new FiniteAutomata(new CharacterRule(appendStr.charAt(0)));
		} else {
			automata = new FiniteAutomata(new StringRule(appendStr.toString()));
		}
		return automata;
	}
}
