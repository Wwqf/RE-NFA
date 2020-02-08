package com.logic;


import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.Thompson;
import com.diagram.stereotype.algorithm.utils.Production;
import com.logic.io.GrammarBuffer;

import java.util.*;

public class ScanningGrammar {

	private List<Production> grammarList;
	private Map<String, FiniteAutomata> automataMap;

	public ScanningGrammar(String filePath) {
		grammarList = new GrammarBuffer(filePath).getGrammarList();
		automataMap = new HashMap<>();
	}

	public void create() {
		for (Production production : grammarList) {
			Thompson thompson = new Thompson(automataMap, production);
			System.out.println(production.getHead() + " : " + production.getBody());
			FiniteAutomata rec = thompson.execute();
			System.out.println(rec.generateDiagram().getTransitionTable());
		}
	}

	public Map<String, FiniteAutomata> getAutomataMap() {
		return automataMap;
	}
}
