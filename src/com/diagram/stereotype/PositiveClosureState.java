package com.diagram.stereotype;

import com.diagram.base.BaseClosureStereotypeDiagram;
import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.closure.ClosureAttrType;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.rule.CharacterRule;
import com.rule.base.BaseRule;

import java.io.*;

public class PositiveClosureState extends BaseClosureStereotypeDiagram {

	public PositiveClosureState(BaseRule rule) {
		super(rule);

		this.closureAttrType = ClosureAttrType.POSITIVE_CLOSURE;
	}

	@Override
	protected void connectClosure() {
		System.out.println("positiveClosure is being executed. [" + rule.getRuleString() + "]");

		BaseStereotypeDiagram diagram = rule.generateDiagram();

		start.addConvertFunc(new CharacterRule(), diagram.start);

		diagram.end.addConvertFunc(new CharacterRule(), end);
		diagram.end.addConvertFunc(new CharacterRule(), diagram.start);
	}
}
