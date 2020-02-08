package com.diagram.unit;

import com.rule.base.BaseRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
	private int tag = 0;
	private StateType type = StateType.NORMAL_STATE;
	private List<TransitionFunc> transitionFuncList = new ArrayList<>();

	public void addTransitionFunc(TransitionFunc... transitionFuncList) {
		this.transitionFuncList.addAll(Arrays.asList(transitionFuncList));
	}

	public void addConvertFunc(BaseRule rule, State nextState) {
		transitionFuncList.add(new TransitionFunc(rule, nextState));
	}

	public void addTransitionFunc(List<TransitionFunc> transitionFuncList) {
		this.transitionFuncList.addAll(transitionFuncList);
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getTag() {
		return tag;
	}

	public void setType(StateType type) {
		this.type = type;
	}

	public StateType getType() {
		return type;
	}

	public List<TransitionFunc> getTransitionFuncList() {
		return transitionFuncList;
	}

	public void setTransitionFuncList(List<TransitionFunc> transitionFuncList) {
		this.transitionFuncList = transitionFuncList;
	}
}
