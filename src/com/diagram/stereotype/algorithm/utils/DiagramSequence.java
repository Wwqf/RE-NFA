package com.diagram.stereotype.algorithm.utils;

import com.diagram.base.BaseStereotypeDiagram;
import com.diagram.unit.State;
import com.diagram.unit.StateType;
import com.diagram.unit.TransitionFunc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 处理有限状态自动机的序号
 */
public class DiagramSequence {

	public static void adjustSequence(int startTag, BaseStereotypeDiagram diagram) {
		adjustSequence(startTag, diagram.getStart());
	}

	public static void adjustSequence(int startTag, State startState) {
		startState.setType(StateType.START_STATE);

		Queue<State> queue = new LinkedList<>();
		queue.add(startState);

		while (!queue.isEmpty()) {
			State s = queue.poll();

			if (s.getTag() == 0) s.setTag(startTag++);
			else continue;

			if (s.getTransitionFuncList().size() == 0) {
				s.setType(StateType.ACCEPT_STATE);
				continue;
			}

			for (TransitionFunc func : s.getTransitionFuncList()) {
				queue.add(func.getNextState());
			}
		}
	}
}
