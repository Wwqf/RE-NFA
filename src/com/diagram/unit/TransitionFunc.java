package com.diagram.unit;

import com.rule.base.BaseRule;

public class TransitionFunc {
	private BaseRule rule;
	private State nextState;

	public TransitionFunc(BaseRule rule, State nextState) {
		this.rule = rule;
		this.nextState = nextState;
	}

	public BaseRule getRule() {
		return rule;
	}

	public State getNextState() {
		return nextState;
	}

	public boolean match(char matchCharacter) {
		return rule.match(matchCharacter);
	}
}
