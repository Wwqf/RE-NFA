package com.rule.base;

import com.diagram.base.BaseStereotypeDiagram;

public abstract class BaseRule {
	public abstract boolean match(char matchCharacter);
	public abstract BaseStereotypeDiagram generateDiagram();
	public abstract String getRuleString();
}
