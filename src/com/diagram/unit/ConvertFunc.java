package com.diagram.unit;

import com.rule.base.BaseRule;

public class ConvertFunc {
	private BaseRule rule;
	private State nextState;

	public ConvertFunc(BaseRule rule, State nextState) {
		this.rule = rule;
		this.nextState = nextState;
	}

	public BaseRule getRule() {
		return rule;
	}

	public State getNextState() {
		return nextState;
	}
}
