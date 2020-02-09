package com.rule.base;

import com.diagram.base.BaseStereotypeDiagram;

import java.io.IOException;

/**
 * 复杂性规则，所以继承此规则的类，生成过一遍有限状态自动机，第二遍生成的是第一遍的clone
 */
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
