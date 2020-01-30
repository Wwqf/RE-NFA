package com.rule;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.ConnectStereotypeState;
import com.rule.base.ComplicatedRule;

import java.util.ArrayList;
import java.util.List;

public class StringRule extends ComplicatedRule {

	private String value;
	private List<CharacterRule> rules;
	public StringRule(String value) {
		this.value = value;

		rules = new ArrayList<>();
		for (char c : value.toCharArray()) {
			rules.add(new CharacterRule(c));
		}
	}

	@Override
	public boolean match(char matchCharacter) {
		for (CharacterRule rule : rules) {
			if (rule.match(matchCharacter)) return true;
		}
		return false;
	}

	@Override
	public BaseStereotypeDiagram generateDiagram() {
		BaseStereotypeDiagram diagram = getGenerateClone();

		if (diagram != null) {
			return diagram;
		} else {
			diagram = new ConnectStereotypeState(this);
		}

		setGenerateClone(diagram);
		return diagram;
	}

	@Override
	public String getRuleString() {
		return value;
	}

	public List<CharacterRule> getRules() {
		return rules;
	}
}
