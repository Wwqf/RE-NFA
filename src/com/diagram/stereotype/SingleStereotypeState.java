package com.diagram.stereotype;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.diagram.unit.State;
import com.rule.CharacterRule;
import com.rule.RangeRule;

import java.io.*;

public class SingleStereotypeState extends BaseStereotypeDiagram {

	public SingleStereotypeState() {
		this(new CharacterRule());
	}

	public SingleStereotypeState(CharacterRule rule) {
		System.out.println("Generate a SingleStereotypeState, about Character-{" + rule.getRuleString() + "}");
		start.addConvertFunc(rule, end);
	}

	public SingleStereotypeState(RangeRule rule) {
		System.out.println("Generate a SingleStereotypeState, about Range-[" + rule.getRuleString() + "]");
		start.addConvertFunc(rule, end);
	}
}
