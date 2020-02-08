import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.logic.ScanningGrammar;
import com.test.TestRE;

import java.util.Map;

public class Application {
	private static final String projectPath = "/home/yanuas/CompilationPrinciple/FinalFA/src/data/input/";
	public static void main(String[] args) {
		String id = "id.i";
		String decimal = "decimal.i";
		String test = "test.i";

		ScanningGrammar scanningGrammar = new ScanningGrammar(projectPath + decimal);
		scanningGrammar.create();

		Map<String, FiniteAutomata> automataMap = scanningGrammar.getAutomataMap();
		FiniteAutomata automata = automataMap.get("number");
		System.out.println(automata.matchNFA("12312"));
		System.out.println(automata.matchNFA("12312."));
		System.out.println(automata.matchNFA("12312.0"));
		System.out.println(automata.matchNFA("123.123"));
		System.out.println(automata.matchNFA("123.123E+545"));

//		Application application = new Application();
//		application.testID();
//		application.testDecimal();
//		application.testTest();
	}

	public Application() {

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
