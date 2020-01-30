package com.diagram.base;

import com.diagram.closure.ClosureAttrType;
import com.rule.base.BaseRule;

public abstract class BaseClosureStereotypeDiagram extends BaseStereotypeDiagram {
	public ClosureAttrType closureAttrType;

	protected BaseRule rule;
	public BaseClosureStereotypeDiagram(BaseRule rule) {
		this.rule = rule;

		connectClosure();
	}

	protected abstract void connectClosure();
}
