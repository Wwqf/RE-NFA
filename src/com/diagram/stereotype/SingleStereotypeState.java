package com.diagram.stereotype;

import com.diagram.base.BaseStereotypeDiagram;
import com.rule.CharacterRule;
import com.rule.RangeRule;

public class SingleStereotypeState extends BaseStereotypeDiagram {

	public SingleStereotypeState() {
		this(new CharacterRule());
	}

	public SingleStereotypeState(CharacterRule rule) {
		start.addConvertFunc(rule, accept);
	}

	public SingleStereotypeState(RangeRule rule) {
		start.addConvertFunc(rule, accept);
	}
}
