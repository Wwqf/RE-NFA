package com.diagram.stereotype.algorithm;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.algorithm.utils.DiagramSequence;
import com.diagram.table.TransitionTable;
import com.diagram.unit.TransitionFunc;
import com.rule.base.BaseRule;

public class FiniteAutomata {

	public BaseRule rule;
	public BaseStereotypeDiagram diagram;

	public FiniteAutomata(BaseRule rule) {
		this.rule = rule;
	}

	public FiniteAutomata generateDiagram() {
		diagram = rule.generateDiagram();
		DiagramSequence.adjustSequence(0, diagram);
		return this;
	}

	public String getTransitionTable() {
		return new TransitionTable(diagram.getStart()).process().toString();
	}

	public boolean matchNFA(String patternStr) {
		return NFA.accept(patternStr, diagram.getStart());
	}
}
