package com.diagram.stereotype.utils;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.unit.ConvertFunc;
import com.diagram.unit.State;
import com.diagram.unit.StateType;
import com.rule.CharacterRule;

import java.io.*;
import java.util.*;

public class StereotypeUtils {

	public static void adjustSequence(int startTag, BaseStereotypeDiagram diagram) {
		adjustSequence(startTag, diagram.start);
	}

	public static void adjustSequence(int startTag, State startState) {
		startState.setType(StateType.START_STATE);

		Queue<State> queue = new LinkedList<>();
		queue.add(startState);

		while (!queue.isEmpty()) {
			State s = queue.poll();

			if (s.getTag() == 0) s.setTag(startTag++);
			else continue;

			if (s.getConvertFuncList().size() == 0) {
				s.setType(StateType.END_STATE);
				continue;
			}

			for (ConvertFunc func : s.getConvertFuncList()) {
				queue.add(func.getNextState());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T object) {
		T cloneObj = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.close();

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			cloneObj = (T) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cloneObj;
	}

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
//		System.out.println("Current character{" + pattern.charAt(index) + "} StateSet ("+ str.toString() +")");

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
		if (s.getType() == StateType.END_STATE) return true;

		List<State> rec = acceptChar('ε', s);
		for (State item : rec) {
			if (item.getType() == StateType.END_STATE) return true;
		}
		return false;
	}

	public static List<State> acceptChar(char c, State state) {
		List<State> result = new ArrayList<>();
		for (ConvertFunc func : state.getConvertFuncList()) {
			if (func.getRule().match(c)) {
				result.add(func.getNextState());
			}
			if (func.getRule().match('ε')) {
				result.addAll(acceptChar(c, func.getNextState()));
			}
		}
		return result;
	}
}
