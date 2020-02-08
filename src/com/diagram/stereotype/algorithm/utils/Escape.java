package com.diagram.stereotype.algorithm.utils;

/**
 * 处理转义字符
 */
public class Escape {

	private static char[][] targets = {
			{'t','\t'},
			{'n','\n'},
			{'\\','\\'},
			{'.','.'},
	};

	public static char getEscape(Production production) {
		// 当匹配到转义字符 \ 时调用, 先偏移一位
		production.offset(1);

		char c = production.getChar();
		for (char[] target : targets) {
			if (c == target[0]) {
				production.offset(1);
				return target[1];
			}
		}

		throw new RuntimeException("Escape character error!");
	}
}
