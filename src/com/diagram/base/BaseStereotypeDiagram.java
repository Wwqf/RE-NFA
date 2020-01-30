package com.diagram.base;

import com.diagram.unit.ConvertFunc;
import com.diagram.unit.State;
import com.diagram.unit.StateType;

import java.io.*;
import java.util.*;

public abstract class BaseStereotypeDiagram implements Cloneable{

	public State start = new State();
	public State end = new State();

}
