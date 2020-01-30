package com.diagram.stereotype;

import com.diagram.base.BaseClosureStereotypeDiagram;
import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.closure.ClosureAttrType;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.rule.CharacterRule;
import com.rule.base.BaseRule;

import java.io.*;

public class ZeroOneClosureState extends BaseClosureStereotypeDiagram {

	public ZeroOneClosureState(BaseRule rule) {
		super(rule);

		this.closureAttrType = ClosureAttrType.ZERO_ONE_CLOSURE;
	}

	@Override
	protected void connectClosure() {
		System.out.println("zeroOneClosure is being executed. [" + rule.getRuleString() + "]");

		BaseStereotypeDiagram diagram = rule.generateDiagram();

		start.addConvertFunc(new CharacterRule(), diagram.start);
		start.addConvertFunc(new CharacterRule(), end);

		diagram.end.addConvertFunc(new CharacterRule(), end);
	}

}
