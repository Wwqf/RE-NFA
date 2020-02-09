package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.SingleStereotypeState;
import com.rule.base.SimpleRule;

/**
 * 字符规则，通过匹配一个单字符到达下一个状态，空构造函数表达空转换
 */
public class CharacterRule extends SimpleRule {

	private char value;
	public CharacterRule() {
		this.value = 'ε';
	}

	public CharacterRule(char value) {
		this.value = value;
	}

	public char getCharacter() {
		return value;
	}

	@Override
	public boolean match(char matchCharacter) {
		return matchCharacter == value;
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		return new SingleStereotypeState(this);
	}

	@Override
	public String getRuleString() {
		if (value == '\t') return "\\t";
		else if (value == '\n') return "\\n";
		else if (value == ' ') return "$";
		return String.valueOf(value);
	}
}
