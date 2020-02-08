package com.diagram.stereotype.algorithm;

import com.diagram.unit.State;
import com.diagram.unit.StateType;
import com.diagram.unit.TransitionFunc;

import java.util.ArrayList;
import java.util.List;

public class NFA {

	public static boolean accept(String pattern, State state) {
		return accept(pattern, state, false);
	}

	public static boolean accept(String pattern, State state, boolean isTrim) {
		if (isTrim) pattern = pattern.trim();

		List<State> states = new ArrayList<>();
		states.add(state);
		return accept(0, pattern, states);
	}

	public static boolean accept(int index, String pattern, List<State> states) {
		if (pattern.equals("")) {
			return checkHasEndState(states);
		}

		if (index >= pattern.length()) return false;

		List<State> rec = new ArrayList<>();
		for (State s : states) {
			rec.addAll(acceptChar(pattern.charAt(index), s));
		}

		StringBuilder str = new StringBuilder();
		for (State s : rec) {
			str.append(s.getTag()).append(" ");
		}

		if (rec.size() == 0) return false;
		if (index == pattern.length() - 1 && checkHasEndState(rec)) return true;

		return accept(index + 1, pattern, rec);
	}

	private static boolean checkHasEndState(List<State> states) {
		for (State s : states) {
			if (checkHasEndState(s)) return true;
		}
		return false;
	}

	private static boolean checkHasEndState(State s) {
		if (s.getType() == StateType.ACCEPT_STATE) return true;

		List<State> rec = acceptChar('ε', s);
		for (State item : rec) {
			if (item.getType() == StateType.ACCEPT_STATE) return true;
		}
		return false;
	}

	public static List<State> acceptChar(char c, State state) {
		List<State> result = new ArrayList<>();
		for (TransitionFunc func : state.getTransitionFuncList()) {
			if (func.match(c)) {
				result.add(func.getNextState());
			}
			if (func.match('ε')) {
				result.addAll(acceptChar(c, func.getNextState()));
			}
		}
		return result;
	}

}
