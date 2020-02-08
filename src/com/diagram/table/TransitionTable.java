package com.diagram.table;

import com.diagram.unit.TransitionFunc;
import com.diagram.unit.State;

import java.util.*;

public class TransitionTable {

	private Set<String> terminalSymSet;
	private Map<Integer, Map<String, Set<Integer>>> tables;

	private State startState;

	public TransitionTable(State startState) {
		this.startState = startState;
		terminalSymSet = new HashSet<>();
		tables = new HashMap<>();
	}

	public TransitionTable process() {
		Set<Integer> stateProcessSet = new HashSet<>();
		subProcess(startState, stateProcessSet);
		return this;
	}

	private void subProcess(State state, Set<Integer> stateIsProcess) {
		int tag = state.getTag();
		if (stateIsProcess.contains(state.getTag())) return ;
		stateIsProcess.add(tag);

		Map<String, Set<Integer>> items = new HashMap<>();

		List<TransitionFunc> funcList = state.getTransitionFuncList();

		for (TransitionFunc func : funcList) {
			String terminalSym = func.getRule().getRuleString();
			terminalSymSet.add(terminalSym);

			Set<Integer> nextStateSet = items.get(terminalSym);
			if (nextStateSet == null) {
				nextStateSet = new HashSet<>();
				items.put(terminalSym, nextStateSet);
				nextStateSet.add(func.getNextState().getTag());
			} else {
				nextStateSet.add(func.getNextState().getTag());
			}

			subProcess(func.getNextState(), stateIsProcess);
		}

		tables.put(tag, items);
	}

	public Map<Integer, Map<String, Set<Integer>>> getTables() {
		return tables;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		Set<Integer> keySet = tables.keySet();
		Object[] arrayKeySet = keySet.toArray();
//		Arrays.sort(arrayKeySet);

		Object[] arrayTerminalSymSet = terminalSymSet.toArray();
		Arrays.sort(arrayTerminalSymSet);

		for (Object ts : arrayTerminalSymSet) {
			result.append("\t\t").append(ts);
		}
		result.append("\n");
		for (Object key : arrayKeySet) {
			Map<String, Set<Integer>> items = tables.get((int)key);
			result.append(key).append("\t\t");
			for (Object ts : arrayTerminalSymSet) {
				Set<Integer> stateSet = items.get((String)ts);
				if (stateSet == null) result.append("∅").append("\t\t");
				else {
					String s = stateSet.toString();
					s = '{' + s.substring(1, s.length() - 1) + '}';
					result.append(s).append("\t\t");
				}
			}
			result.append("\n");
		}

		return result.toString();
	}
}
