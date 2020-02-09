package com.diagram.stereotype;

import com.diagram.base.BaseClosureStereotypeDiagram;
import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.closure.ClosureAttrType;
import com.rule.CharacterRule;
import com.rule.base.BaseRule;

/**
 * 传入基本规则，生成一个正闭包有限状态自动机
 */
public class PositiveClosureState extends BaseClosureStereotypeDiagram {

	public PositiveClosureState(BaseRule rule) {
		super(rule);

		this.closureAttrType = ClosureAttrType.POSITIVE_CLOSURE;
	}

	@Override
	protected void connectClosure() {
		BaseStereotypeDiagram diagram = rule.generateDiagram();

		start.addConvertFunc(new CharacterRule(), diagram.getStart());

		diagram.getAccept().addConvertFunc(new CharacterRule(), accept);
		diagram.getAccept().addConvertFunc(new CharacterRule(), diagram.getStart());
	}
}
