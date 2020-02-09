package com.diagram.stereotype;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.unit.State;
import com.rule.CharacterRule;
import com.rule.CombinationRule;
import com.rule.CountingRule;
import com.rule.StringRule;
import com.rule.base.BaseRule;

import java.util.List;

/**
 * 连接状态定式, 为多条规则生成一个有限状态自动机
 */
public class ConnectStereotypeState extends BaseStereotypeDiagram {

	public ConnectStereotypeState(StringRule rule) {
		solveStringRule(rule);
	}

	public ConnectStereotypeState(CombinationRule rule) {
		solveCombinationRule(rule);
	}

	public ConnectStereotypeState(CountingRule rule) {
	}

	private void solveStringRule(StringRule rule) {
		State current = start;
		for (CharacterRule r : rule.getRules()) {
			State next = new State();
			current.addConvertFunc(r, next);

			current = next;
		}
		accept = current;
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
		this.accept = state.accept;
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
	private void solveCountRule(CountingRule rule) {
		BaseRule item = rule.rule;
		int least = rule.least, most = rule.most;
	}

	public ConnectStereotypeState(BaseStereotypeDiagram initDiagram) {
		start = initDiagram.getStart();
		accept = initDiagram.getAccept();
	}

	public ConnectStereotypeState addDiagram(BaseStereotypeDiagram diagram) {
		accept.addConvertFunc(new CharacterRule(), diagram.getStart());
		accept = diagram.getAccept();
		return this;
	}
}
