package com.logic;


import com.logic.io.Grammar;
import com.logic.io.GrammarBuffer;
import com.rule.*;
import com.rule.base.BaseRule;
import com.rule.enums.ClosureType;

import java.util.*;

public class ScanningGrammar {
	private static class CurrentProduction {
		String body;
		int index;

		char getChar() {
			return body.charAt(index);
		}

		char getChar(int offset) {
			return body.charAt(index + offset);
		}

		void offset(int offsetValue) {
			index += offsetValue;
		}

		void inc() {
			++index;
		}

		void skipSpaces() {
			while (getChar() == ' ' || getChar() == '\t' || getChar() == '\n') {
				inc();
			}
		}
	}

	private List<Grammar> grammarList;
	private Map<String, RuleDiagram> ruleDiagrams;
	private CurrentProduction curProc;

	private BaseRule preBaseRule = null;

	public ScanningGrammar(String filePath) {
		grammarList = new GrammarBuffer(filePath).getGrammarList();
		ruleDiagrams = new HashMap<>();
		curProc = new CurrentProduction();
	}

	public void create() {
		for (Grammar grammar : grammarList) {
			String head = grammar.head, body = grammar.body;
			System.out.println("Current processing production -{" + head + "-->" + body + "}");

			curProc.body = body;
			curProc.index = 0;

			processProduction(head);
		}
	}

	private void processProduction(String head) {
		BaseRule rule = processProductionBody();
		ruleDiagrams.put(head, new RuleDiagram(rule));
	}

	private BaseRule processProductionBody() {
		CombinationRule result = new CombinationRule();

		char c;
		for ( ; curProc.index < curProc.body.length(); ) {
			curProc.skipSpaces();
			c = curProc.body.charAt(curProc.index);
			BaseRule rule = processBodyCharacter(c);
			if (rule != null)
				result.addRule(rule);
		}

		if (result.getRules().size() == 1) {
			return result.getRules().get(0);
		}
		return result;
	}

	private BaseRule processBodyCharacter(char c) {
		switch (c) {
			case '{':
				BaseRule braceRule = braceRule();
				braceRule = hasClosureAttr(braceRule);
				preBaseRule = braceRule;
				return braceRule;
			case '[':
				BaseRule bracketRule = bracketRule();
				bracketRule = hasClosureAttr(bracketRule);
				preBaseRule = bracketRule;
				return hasClosureAttr(bracketRule);
			case '(':
				BaseRule parenthesisRule = parenthesisRule();
				parenthesisRule = hasClosureAttr(parenthesisRule);
				preBaseRule = parenthesisRule;
				return hasClosureAttr(parenthesisRule);
			case '\\':
				BaseRule rule = new CharacterRule(getEscape());
				preBaseRule = rule;
				return rule;
		}
		CharacterRule rule = new CharacterRule(c);
		preBaseRule = rule;
		curProc.inc();
		return rule;
	}

	private BaseRule braceRule() {
		/*
		 * {digit}
		 * {1, 6}
		 */

		// '{'
		curProc.inc();
		char c;

		boolean isDigit = Character.isDigit(curProc.getChar());
		if (isDigit) {
			// range rule
			int left = 0, right = 0;
			while ((c = curProc.getChar()) != ',') {
				left = left * 10 + (c - '0');
				curProc.inc();
				curProc.skipSpaces();
			}

			curProc.inc();
			curProc.skipSpaces();

			while ((c = curProc.getChar()) != '}') {
				right = right * 10 + (c - '0');
				curProc.inc();
			}

			// '}'
			curProc.inc();
			if (preBaseRule != null) {
				return new CountRule(preBaseRule, left, right);
			}
			throw new RuntimeException("grammar error!");
		} else {
			// production head name
			StringBuilder headName = new StringBuilder();

			while ((c = curProc.getChar()) != '}') {
				headName.append(c);
				curProc.inc();
			}

			RuleDiagram ruleDiagram = ruleDiagrams.get(headName.toString());
			if (ruleDiagram == null) {
				throw new RuntimeException("Production rule is error!");
			}

			// '}'
			curProc.inc();
			return ruleDiagram.rule;
		}
	}

	private BaseRule bracketRule() {
		/*
		 * [abA-G0-9s]
		 * [+-ac]
		 * [\.sync]
		 * [^A-Z]
		 * [ \n\t]
		 */

		curProc.inc();

		OrRule orRule = new OrRule();

		if (curProc.getChar() == '^') {
			orRule.isNegate = true;
			curProc.inc();
		}

		Stack<CharacterRule> ruleStack = new Stack<>();
		char c;
		while ((c = curProc.getChar()) != ']') {
			switch (c) {
				case '\\':
					CharacterRule cr0 = new CharacterRule(getEscape());
					ruleStack.push(cr0);
					break;
				case '-':
					char left = ruleStack.peek().getCharacter();
					char right = curProc.getChar(1);

					if ( (Character.isLetter(left) && Character.isLetter(right)) ||
							(Character.isDigit(left) && Character.isDigit(right)) ) {
						// A-Z, e-s     0-9, 2-5
						if (right <= left) System.out.println("grammar is wrong!");
						ruleStack.pop();
						orRule.addRule(new RangeRule(left, right));
						curProc.inc();
					} else {
						// A-9, a-0
						CharacterRule cr1 = new CharacterRule(c);
						ruleStack.push(cr1);
					}
					break;
				default:
					CharacterRule cr2 = new CharacterRule(c);
					ruleStack.push(cr2);
					break;
			}

			curProc.inc();
		}

		while (!ruleStack.empty()) {
			orRule.addRule(ruleStack.pop());
		}

		curProc.inc();
		return orRule;
	}

	private BaseRule parenthesisRule() {
		/*
		 * (\.{digit}+)?
		 * (E[+-]?{digit}+)?
		 * ({letter_ul} | {digit})*
		 * ([+-]? ({letter_ul} | {digit})?)*
		 */

		curProc.inc();

		boolean isOrRule = false;
		Queue<BaseRule> queues = new LinkedList<>();

		// strings
		StringBuilder temporary = new StringBuilder();

		for ( ; curProc.index < curProc.body.length(); ) {
			curProc.skipSpaces();
			char c = curProc.getChar();
			switch (c) {
				case '(':
					if (temporary.length() > 1) {
						// is strings
						queues.add(new StringRule(temporary.toString()));
					} else if (temporary.length() == 1){
						// is char
						queues.add(new CharacterRule(temporary.charAt(0)));
					}
					temporary.delete(0, temporary.length());
					queues.add(parenthesisRule());
					break;
				case ')':
					if (temporary.length() > 1) {
						// is strings
						queues.add(new StringRule(temporary.toString()));
					} else if (temporary.length() == 1){
						// is char
						queues.add(new CharacterRule(temporary.charAt(0)));
					}
					temporary.delete(0, temporary.length());

					curProc.inc();
					if (isOrRule) {
						OrRule orRule = new OrRule();
						while (!queues.isEmpty()) {
							orRule.addRule(queues.poll());
						}
						return orRule;
					} else {
						CombinationRule combinationRule = new CombinationRule();
						while (!queues.isEmpty()) {
							combinationRule.addRule(queues.poll());
						}
						return combinationRule;
					}
				case '|':
					if (temporary.length() > 1) {
						// is strings
						queues.add(new StringRule(temporary.toString()));
					} else if (temporary.length() == 1){
						// is char
						queues.add(new CharacterRule(temporary.charAt(0)));
					}
					temporary.delete(0, temporary.length());

					isOrRule = true;
					curProc.inc();
					curProc.skipSpaces();
					break;
			}
			BaseRule rule = processBodyCharacter(curProc.getChar());
			if (rule instanceof CharacterRule) {
				temporary.append(((CharacterRule) rule).getCharacter());
			} else {
				if (temporary.length() > 1) {
					// is strings
					queues.add(new StringRule(temporary.toString()));
				} else if (temporary.length() == 1){
					// is char
					queues.add(new CharacterRule(temporary.charAt(0)));
				}
				temporary.delete(0, temporary.length());

				queues.add(rule);
			}
		}

		throw new RuntimeException("grammar error!");
	}

	private BaseRule hasClosureAttr(BaseRule rule) {
		/*
		 *  Maybe more than string subscripts, try catch there.
		 */
		try {
			if (curProc.getChar() == '+') {
				curProc.inc();
				return new ClosureRule(rule, ClosureType.POSITIVE_CLOSURE);
			}
			else if (curProc.getChar() == '*') {
				curProc.inc();
				return new ClosureRule(rule, ClosureType.KLEENE_CLOSURE);
			}
			else if (curProc.getChar() == '?') {
				curProc.inc();
				return new ClosureRule(rule, ClosureType.ZERO_ONE_CLOSURE);
			}
		} catch (Exception ignored) {
		}
		return rule;
	}

	private char getEscape() {
		char[][] targets = {
				{'t','\t'},
				{'n','\n'},
				{'\\','\\'},
				{'.','.'},
		};

		char c = curProc.getChar(1);
		for (char[] target : targets) {
			if (c == target[0]) {
				// TODO \n has Bug, need inc()
				curProc.offset(1);
				return target[1];
			}
		}

		throw new RuntimeException("grammar error!");
	}

	public Map<String, RuleDiagram> getRuleDiagrams() {
		return ruleDiagrams;
	}

	public RuleDiagram getRuleDiagram(String key) {
		return ruleDiagrams.get(key);
	}
}
