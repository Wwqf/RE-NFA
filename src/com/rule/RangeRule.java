package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.SingleStereotypeState;
import com.rule.base.BaseRule;
import com.rule.base.SimpleRule;

/**
 * 范围规则，连词符的应用规则
 * [a-z] [0-9]
 */
public class RangeRule extends SimpleRule {

	private char left, right;
	public RangeRule(char left, char right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean match(char matchCharacter) {
		return (matchCharacter >= left) && (matchCharacter <= right);
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		return new SingleStereotypeState(this);
	}

	@Override
	public String getRuleString() {
		return left + "-" + right;
	}

	public char getLeft() {
		return left;
	}

	public char getRight() {
		return right;
	}
}
