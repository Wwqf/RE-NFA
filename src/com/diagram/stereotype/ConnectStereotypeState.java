package com.diagram.stereotype;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.diagram.unit.State;
import com.rule.CharacterRule;
import com.rule.CombinationRule;
import com.rule.CountRule;
import com.rule.StringRule;
import com.rule.base.BaseRule;

import java.io.*;
import java.util.List;

public class ConnectStereotypeState extends BaseStereotypeDiagram {

	public ConnectStereotypeState(StringRule rule) {
		System.out.println("Use ConnectStereotype to process StringRule-{" + rule.getRuleString() + "}");
		solveStringRule(rule);
	}

	public ConnectStereotypeState(CombinationRule rule) {
		System.out.println("Use ConnectStereotype to process CombinationRule-{" + rule.getRuleString() + "}");
		solveCombinationRule(rule);
	}

	public ConnectStereotypeState(CountRule rule) {
		System.out.println("Use ConnectStereotype to process CountRule-{" + rule.getRuleString() + "}");
	}

	private void solveStringRule(StringRule rule) {
		State current = start;
		for (CharacterRule r : rule.getRules()) {
			State next = new State();
			current.addConvertFunc(r, next);

			current = next;
		}
		end = current;
	}

	private void solveCombinationRule(CombinationRule rule) {
		StringBuilder toOneRule = new StringBuilder();
		List<BaseRule> rules = rule.getRules();

		ConnectStereotypeState state = null;
		if (rules.get(0) instanceof CharacterRule) {
			toOneRule.append(((CharacterRule) rules.get(0)).getCharacter());
		} else {
			state = new ConnectStereotypeState(rules.get(0).generateDiagram());
		}

		for (int i = 1; i < rules.size(); i++) {
			if (rules.get(i) instanceof CharacterRule) {
				toOneRule.append(((CharacterRule) rules.get(i)).getCharacter());
			} else {
				state = processConnectCharacter(toOneRule, state);

				if (state == null) {
					state = new ConnectStereotypeState(rules.get(i).generateDiagram());
				} else {
					state.addDiagram(rules.get(i).generateDiagram());
				}

			}
		}

		state = processConnectCharacter(toOneRule, state);
		this.start = state.start;
		this.end = state.end;
	}

	private ConnectStereotypeState processConnectCharacter(StringBuilder toOneRule, ConnectStereotypeState state) {
		if (toOneRule.length() > 1) {
			if (state == null) {
				state = new ConnectStereotypeState(new StringRule(toOneRule.toString()));
			} else {
				state.addDiagram(new StringRule(toOneRule.toString()).generateDiagram());
			}

			toOneRule.delete(0, toOneRule.length());
		} else if (toOneRule.length() == 1) {
			if (state == null) {
				state = new ConnectStereotypeState(new SingleStereotypeState(new CharacterRule(toOneRule.charAt(0))));
			} else {
				state.addDiagram(new SingleStereotypeState(new CharacterRule(toOneRule.charAt(0))));
			}
			toOneRule.delete(0, toOneRule.length());
		}
		return state;
	}

	@Deprecated
	private void solveCountRule(CountRule rule) {
		BaseRule item = rule.rule;
		int least = rule.least, most = rule.most;
	}

	public ConnectStereotypeState(BaseStereotypeDiagram initDiagram) {
		start = initDiagram.start;
		end = initDiagram.end;
	}

	public ConnectStereotypeState addDiagram(BaseStereotypeDiagram diagram) {
		end.addConvertFunc(new CharacterRule(), diagram.start);
		end = diagram.end;
		return this;
	}
}
