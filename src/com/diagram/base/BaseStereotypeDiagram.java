package com.diagram.base;

import com.diagram.unit.State;

/**
 * 定式有限状态自动机的基类, 任何一个定式有限状态自动机有一个开始状态和一个接受状态
 */
public abstract class BaseStereotypeDiagram implements Cloneable {

	protected State start = new State();
	protected State accept = new State();

	public State getStart() {
		return start;
	}

	public void setStart(State start) {
		this.start = start;
	}

	public State getAccept() {
		return accept;
	}

	public void setAccept(State accept) {
		this.accept = accept;
	}
}
