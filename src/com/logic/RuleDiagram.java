package com.logic;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.diagram.table.ConvertTable;
import com.rule.base.BaseRule;

public class RuleDiagram {
	public BaseRule rule;
	public BaseStereotypeDiagram diagram;

	public RuleDiagram(BaseRule rule) {
		this.rule = rule;
		this.diagram = rule.generateDiagram();
		StereotypeUtils.adjustSequence(0, this.diagram.start);
	}

	public String getConvertTableString() {
		ConvertTable table = new ConvertTable(this.diagram.start);
		table.process();
		return table.toString();
	}

	public boolean match(String pattern) {
		return StereotypeUtils.accept(pattern, this.diagram.start);
	}
}
