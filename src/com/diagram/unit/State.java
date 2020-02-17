package com.diagram.unit;

import com.rule.base.BaseRule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class State {
	private int tag = 0;
	private StateType type = StateType.NORMAL_STATE;
	private List<ConvertFunc> convertFuncList = new ArrayList<>();

	public void addConvertFunc(ConvertFunc ...ConvertFuncList) {
		this.convertFuncList.addAll(Arrays.asList(ConvertFuncList));
	}

	public void addConvertFunc(BaseRule rule, State nextState) {
		convertFuncList.add(new ConvertFunc(rule, nextState));
	}

	public void addConvertFunc(List<ConvertFunc> ConvertFuncList) {
		this.convertFuncList.addAll(ConvertFuncList);
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

	public List<ConvertFunc> getConvertFuncList() {
		return convertFuncList;
	}

	public void setConvertFuncList(List<ConvertFunc> convertFuncList) {
		this.convertFuncList = convertFuncList;
	}
}
