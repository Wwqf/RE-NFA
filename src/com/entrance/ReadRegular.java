package com.entrance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diagram.stereotype.algorithm.FiniteAutomata;
import com.diagram.stereotype.algorithm.Thompson;
import com.diagram.stereotype.algorithm.utils.Production;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadRegular {

	private String projectPath;
	private String inputPath;
	private String outputPath;
	private String samplePath;
	private JSONObject json;

	private List<Production> productionList;
	private Map<String, FiniteAutomata> automataMap;

	public ReadRegular(String filename) {
		automataMap = new HashMap<>();

		/*
		 * 初始化文件路径
		 * 包括输入文件，输出文件，以及样例文件
		 */
		File file = new File("");

		try {
			projectPath = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		inputPath = projectPath + "/src/data/input/";
		outputPath = projectPath + "/src/data/output/";
		samplePath = projectPath + "/src/data/sample/" + filename;

		System.out.println("Tip: Make sure the /src/data/[input/ output/ sample/] path exists.");
	}

	/**
	 * 读取样例文件
	 * @return
	 */
	public ReadRegular analysisSampleFile() {
		File file = new File(samplePath);
		try {
			InputStream stream = new FileInputStream(file);
			int avail = stream.available();
			byte[] bytes = new byte[avail];
			stream.read(bytes);
			json = JSON.parseObject(new String(bytes));
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 读取输入文件
	 * @return
	 */
	public ReadRegular readInputFile() {
		productionList = new ArrayList<>();
		JSONArray template = json.getJSONArray("template");
		for (Object filename : template) {
			productionList.addAll(new GrammarBuffer(inputPath + filename).getGrammarList());
		}
		return this;
	}

	/**
	 * 生成有限状态自动机
	 * @return
	 */
	public ReadRegular generateDiagram() {
		for (Production production : productionList) {
			Thompson thompson = new Thompson(automataMap, production);
			FiniteAutomata rec = thompson.execute();
			automataMap.put(production.getHead(), rec);
		}
		return this;
	}

	/**
	 * 测试样例
	 */
	public void testcase() {
		JSONArray testcase = json.getJSONArray("testcase");
		for (Object productionHeadName : testcase) {
			FiniteAutomata automata = automataMap.get((String)productionHeadName);
			if (automata == null) {
				throw new RuntimeException("The testcase is abnormal!");
			}
			automata.generateDiagram();

			JSONObject obj = json.getJSONObject((String) productionHeadName);
			JSONArray value = obj.getJSONArray("value");

			for (Object patternStr : value) {
				boolean isAccept = automata.matchNFA(String.valueOf(patternStr));
				if (isAccept) {
					System.out.println("测试样例 (" + productionHeadName + ": " + patternStr + ") 正确");
				} else {
					System.out.println("测试样例 (" + productionHeadName + ": " + patternStr + ") 错误");
				}
			}
		}
	}
}
