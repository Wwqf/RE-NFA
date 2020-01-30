import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.stereotype.*;
import com.diagram.stereotype.utils.StereotypeUtils;
import com.diagram.table.ConvertTable;
import com.diagram.unit.ConvertFunc;
import com.diagram.unit.State;
import com.logic.ScanningGrammar;
import com.rule.*;
import com.rule.ClosureRule;
import com.rule.enums.ClosureType;
import com.test.TestRE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
	private static final String projectPath = "/home/yanuas/CompilationPrinciple/FinalFA/src/data/input/";
	public static void main(String[] args) {
		String id = "id.i";
		String decimal = "decimal.i";
		String test = "test.i";

		Application application = new Application();
//		application.testCharacterRule();
//		application.testStringRule();
//		application.testClosure();
//		application.testOrRule();
//		application.testAcceptChar();
//		application.testConvertTable();

		application.testID();
		application.testDecimal();
		application.testTest();
	}

	public Application() {

	}

	private void testCharacterRule() {
		CharacterRule characterRule = new CharacterRule('a');
		SingleStereotypeState singleStereotypeState = (SingleStereotypeState) characterRule.generateDiagram();
		StereotypeUtils.adjustSequence(0, singleStereotypeState);
		System.out.println(characterRule.getRuleString() + " % (a) --> " + StereotypeUtils.accept("a", singleStereotypeState.start));
		System.out.println(characterRule.getRuleString() + " % (as) --> " + StereotypeUtils.accept("as", singleStereotypeState.start));
		System.out.println(characterRule.getRuleString() + " % (c) --> " + StereotypeUtils.accept("c", singleStereotypeState.start));
		System.out.println(characterRule.getRuleString() + " % () --> " + StereotypeUtils.accept("", singleStereotypeState.start));
		System.out.println();
	}

	private void testStringRule() {
		StringRule stringRule = new StringRule("NFA");
		ConnectStereotypeState connectStereotypeState0 = (ConnectStereotypeState) stringRule.generateDiagram();

		StereotypeUtils.adjustSequence(0, connectStereotypeState0);
		System.out.println(stringRule.getRuleString() + " % (NFA) --> " + StereotypeUtils.accept("NFA", connectStereotypeState0.start));
		System.out.println(stringRule.getRuleString() + " % (NFA1) --> " + StereotypeUtils.accept("NFA1", connectStereotypeState0.start));
		System.out.println(stringRule.getRuleString() + " % (NF) --> " + StereotypeUtils.accept("NF", connectStereotypeState0.start));
		System.out.println(stringRule.getRuleString() + " % (ANF) --> " + StereotypeUtils.accept("ANF", connectStereotypeState0.start));

		System.out.println();
	}

	private void testClosure() {
		StringRule rule = new StringRule("hello");
		ConnectStereotypeState state = (ConnectStereotypeState) rule.generateDiagram();

		KleeneClosureState kleeneClosure = new KleeneClosureState(rule);
		StereotypeUtils.adjustSequence(0, kleeneClosure);

		PositiveClosureState positiveClosure = new PositiveClosureState(rule);
		StereotypeUtils.adjustSequence(0, positiveClosure);

		ZeroOneClosureState zeroOneClosure = new ZeroOneClosureState(rule);
		StereotypeUtils.adjustSequence(0, zeroOneClosure);

		ClosureRule closureRule = new ClosureRule(rule, ClosureType.ZERO_ONE_CLOSURE);
		BaseStereotypeDiagram diagram = closureRule.generateDiagram();
		StereotypeUtils.adjustSequence(0, diagram);

		System.out.println(closureRule.getRuleString() + " % () --> " + StereotypeUtils.accept("", diagram.start));
		System.out.println(closureRule.getRuleString() + " % (hello) --> " + StereotypeUtils.accept("hello", diagram.start));
		System.out.println(closureRule.getRuleString() + " % (hello!) --> " + StereotypeUtils.accept("hello!", diagram.start));
		System.out.println(closureRule.getRuleString() + " % (hellohello) --> " + StereotypeUtils.accept("hellohello", diagram.start));
		System.out.println(closureRule.getRuleString() + " % (hellhoello) --> " + StereotypeUtils.accept("hellhoello", diagram.start));

		System.out.println();
	}

	private void testOrRule() {
		StringRule stringRule = new StringRule("as");
		CharacterRule characterRule = new CharacterRule('_');

		RangeRule rangeRule = new RangeRule('a', 'z');

		OrRule orRule0 = new OrRule();
		orRule0.addRule(stringRule);

		OrRule orRule1 = new OrRule();
		orRule1.addRule(stringRule);
		orRule1.addRule(characterRule);
		orRule1.addRule(rangeRule);


		OrStereotypeState state1 = new OrStereotypeState(orRule0);
		OrStereotypeState state2 = new OrStereotypeState(orRule1);
		StereotypeUtils.adjustSequence(0, state1);
		StereotypeUtils.adjustSequence(0, state2);
		System.out.println(orRule1.getRuleString() + " % (as) --> " + StereotypeUtils.accept("as", state2.start));
		System.out.println(orRule1.getRuleString() + " % (a) --> " + StereotypeUtils.accept("a", state2.start));
		System.out.println(orRule1.getRuleString() + " % (az) --> " + StereotypeUtils.accept("az", state2.start));
		System.out.println(orRule1.getRuleString() + " % (sa) --> " + StereotypeUtils.accept("sa", state2.start));
		System.out.println(orRule1.getRuleString() + " % (_) --> " + StereotypeUtils.accept("_", state2.start));
	}

	private void testAcceptChar() {
		State state1 = new State();
		State state2 = new State();
		State state3 = new State();
		State state4 = new State();
		State state5 = new State();
		State state6 = new State();
		State state7 = new State();
		State state8 = new State();
		State state9 = new State();
		State state10 = new State();


		state1.addConvertFunc(new CharacterRule(), state2);
		state1.addConvertFunc(new CharacterRule(), state3);
		state1.addConvertFunc(new CharacterRule('a'), state10);


		state2.addConvertFunc(new CharacterRule('a'), state4);
		state4.addConvertFunc(new CharacterRule('s'), state5);

		state3.addConvertFunc(new CharacterRule(), state6);
		state6.addConvertFunc(new CharacterRule('0'), state7);
		state7.addConvertFunc(new CharacterRule('_'), state8);

		state8.addConvertFunc(new CharacterRule('9'), state9);

		List<State> list1 = StereotypeUtils.acceptChar('a', state1);
		List<State> list2 = StereotypeUtils.acceptChar('0', state1);
		System.out.println();
	}

	private void testConvertTable() {

		State state1 = new State();
		State state2 = new State();
		State state3 = new State();
		State state4 = new State();
		State state5 = new State();
		State state6 = new State();
		State state7 = new State();
		State state8 = new State();
		State state9 = new State();
		State state10 = new State();


		state1.addConvertFunc(new CharacterRule(), state2);
		state1.addConvertFunc(new CharacterRule(), state3);
		state1.addConvertFunc(new RangeRule('a', 'c'), state10);


		state2.addConvertFunc(new CharacterRule('a'), state4);
		state4.addConvertFunc(new CharacterRule('s'), state5);

		state3.addConvertFunc(new CharacterRule(), state6);
		state6.addConvertFunc(new CharacterRule('0'), state7);
		state7.addConvertFunc(new CharacterRule('_'), state8);

		state8.addConvertFunc(new CharacterRule('9'), state9);

		StereotypeUtils.adjustSequence(1, state1);

		ConvertTable table = new ConvertTable(state1);
		table.process();
		System.out.println(table.toString());
	}

	private void testID() {
		TestRE re = new TestRE(TestRE.id);
		re.test("letter_ul", "A");
		re.test("letter_ul", "B");
		re.test("letter_ul", "abc");
		re.test("letter_ul", "_abc");
		re.test("letter_ul", "_");
		re.test("letter_ul", "a");
		re.test("letter_ul", "g");

		re.test("digit", "2");
		re.test("digit", "12");
		re.test("digit", "123");
		re.test("digit", "123.1");
		re.test("digit", "A123.1");

		re.test("id", "value");
		re.test("id", "_value");
		re.test("id", "_0value");
		re.test("id", "0value");
		re.test("id", "0v_0alue_");
		re.test("id", "&v_0alue_");
		re.test("id", "v&0alue_");
	}

	private void testDecimal() {
		TestRE re = new TestRE(TestRE.decimal);
		re.test("digit", "12");
		re.test("number", "12");
		re.test("number", "12312.");
		re.test("number", "123");
		re.test("number", "00123");
		re.test("number", "123.123");
		re.test("number", "123.123E+545");
		re.test("number", "123.123E-545");
		re.test("number", "123.123-E545");
		re.test("number", "123.0E+545");
		re.test("number", "1265434233.0");
	}

	private void testTest() {
		TestRE re = new TestRE(TestRE.test);
		re.test("int", "int");
		re.test("int", "ints");
		re.test("double", "double");
		re.test("double", "_double");

		re.test("relop", ">");
		re.test("relop", "!=");
		re.test("relop", "!==");

		re.test("ws", " ");
		re.test("ws", "\t");
		re.test("ws", "\n");
		re.test("ws", "  ");

		re.test("type", "int");
		re.test("type", "double");
		re.test("type", " int ", true);

//		 {type}{ws}+{id}*;
		re.test("dec", "int val;");
		re.test("dec", "int val\t\t\t\t _hello;");
		re.test("dec", "int val\t\t\n _1_1_2;");

	}
}
