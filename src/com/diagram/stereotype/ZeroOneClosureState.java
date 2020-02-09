package com.diagram.stereotype;

import com.diagram.base.BaseClosureStereotypeDiagram;
import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.closure.ClosureAttrType;
import com.rule.CharacterRule;
import com.rule.base.BaseRule;

/**
 * 传入基本规则，生成一个零一闭包有限状态自动机
 */
public class ZeroOneClosureState extends BaseClosureStereotypeDiagram {

	public ZeroOneClosureState(BaseRule rule) {
		super(rule);

		this.closureAttrType = ClosureAttrType.ZERO_ONE_CLOSURE;
	}

	@Override
	protected void connectClosure() {
		BaseStereotypeDiagram diagram = rule.generateDiagram();

		start.addConvertFunc(new CharacterRule(), diagram.getStart());
		start.addConvertFunc(new CharacterRule(), accept);

		diagram.getAccept().addConvertFunc(new CharacterRule(), accept);
	}

}
