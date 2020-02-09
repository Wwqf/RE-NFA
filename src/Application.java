import com.entrance.ReadRegular;

public class Application {
	public static void main(String[] args) {
		ReadRegular readRegular = new ReadRegular("sample.json");
		readRegular.analysisSampleFile().readInputFile().generateDiagram().testcase();
	}
}
