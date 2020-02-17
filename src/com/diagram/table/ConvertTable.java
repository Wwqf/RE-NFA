package com.diagram.table;

import com.diagram.unit.ConvertFunc;
import com.diagram.unit.State;

import java.util.*;

public class ConvertTable {

	private Set<String> terminalSymSet;
	private Map<Integer, Map<String, Set<Integer>>> tables;

	private State startState;

	public ConvertTable(State startState) {
		this.startState = startState;
		terminalSymSet = new HashSet<>();
		tables = new HashMap<>();
	}

	public void process() {
		Set<Integer> stateProcessSet = new HashSet<>();
		subProcess(startState, stateProcessSet);
	}

	private void subProcess(State state, Set<Integer> stateIsProcess) {
		int tag = state.getTag();
		if (stateIsProcess.contains(state.getTag())) return ;
		stateIsProcess.add(tag);

		Map<String, Set<Integer>> items = new HashMap<>();

		List<ConvertFunc> funcList = state.getConvertFuncList();

		for (ConvertFunc func : funcList) {
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
			result.append("\t\t\t").append(ts);
		}
		result.append("\n");
		for (Object key : arrayKeySet) {
			Map<String, Set<Integer>> items = tables.get((int)key);
			result.append(key).append("\t\t\t");
			for (Object ts : arrayTerminalSymSet) {
				Set<Integer> stateSet = items.get((String)ts);
				if (stateSet == null) result.append("[]").append("\t\t\t");
				else result.append(stateSet).append("\t\t\t");
			}
			result.append("\n");
		}

		return result.toString();
	}
}
