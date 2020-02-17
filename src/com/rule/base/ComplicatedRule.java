package com.rule.base;

import com.diagram.base.BaseStereotypeDiagram;

import java.io.IOException;

public abstract class ComplicatedRule extends BaseRule {
	protected boolean isGenerate = false;
	protected BaseStereotypeDiagram temporaryCloneDiagram = null;

	public BaseStereotypeDiagram getGenerateClone() {
		if (isGenerate) {
			// Todo A copy of the object should be returned.
		}
		return null;
	}

	public void setGenerateClone(BaseStereotypeDiagram clone) {
		isGenerate = true;
		temporaryCloneDiagram = clone;
	}
}
