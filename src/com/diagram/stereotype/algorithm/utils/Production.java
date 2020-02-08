package com.diagram.stereotype.algorithm.utils;

/**
 * 产生式
 *
 */
public class Production {
	private String head;
	private String body;
	private int index = 0;

	public Production(String head, String body) {
		this.head = head;
		this.body = body;
	}

	public char getChar() {
		return body.charAt(index);
	}

	public char getChar(int offset) {
		return body.charAt(index + offset);
	}

	public void offset(int offsetValue) {
		index += offsetValue;
	}

	public char skipSpaces() {
		while (getChar() == ' ' ||
				getChar() == '\t' ||
				getChar() == '\n') {
			offset(1);
		}
		return getChar();
	}

	public String getHead() {
		return head;
	}

	public String getBody() {
		return body;
	}

	public int getIndex() {
		return index;
	}

	public boolean indexIsEnd() {
		return index >= body.length();
	}
}
