package com.diagram.stereotype;

import com.diagram.base.BaseClosureStereotypeDiagram;
import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.closure.ClosureAttrType;
import com.rule.CharacterRule;
import com.rule.base.BaseRule;

public class KleeneClosureState extends BaseClosureStereotypeDiagram {

	public KleeneClosureState(BaseRule rule) {
		super(rule);

		this.closureAttrType = ClosureAttrType.KLEENE_CLOSURE;
	}

	@Override
	protected void connectClosure() {
		BaseStereotypeDiagram diagram = rule.generateDiagram();

		start.addConvertFunc(new CharacterRule(), diagram.getStart());
		start.addConvertFunc(new CharacterRule(), accept);

		diagram.getAccept().addConvertFunc(new CharacterRule(), accept);
		diagram.getAccept().addConvertFunc(new CharacterRule(), diagram.getStart());
	}

}
