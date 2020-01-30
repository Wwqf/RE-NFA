package com.logic.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GrammarBuffer {
	private BufferedReader reader;
	private List<Grammar> grammarList;

	public GrammarBuffer(String filePath) {
		grammarList = new ArrayList<>();
		read(filePath);
	}

	private void read(String filePath) {
		File file = new File(filePath);

		if (!file.exists()) {
			System.out.println("The file was not found.");
			System.exit(1);
		}

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Grammar> getGrammarList() {
		String production;

		try {
			while ((production = reader.readLine()) != null) {
				if (production.trim().equals("")) continue;

				String[] strings = production.split("->");
				if (strings.length != 2) {
					System.out.println("The production rule has wrong.");
					System.exit(1);
				}
				Grammar grammar = new Grammar(strings[0].trim(), strings[1].trim());
				grammarList.add(grammar);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return grammarList;
	}
}
