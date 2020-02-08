package com.diagram.base;

import com.diagram.stereotype.closure.ClosureAttrType;
import com.rule.base.BaseRule;

/**
 * 闭包有限状态自动机的基类，继承自定式有限状态自动机,
 * 由此可知, 闭包有限状态自动机也是一个定式有限状态自动机,
 * 含有一个闭包属性(r*, r+, r?), 在构造时传入规则，调用connectClosure()方法构建有限状态自动机
 */
public abstract class BaseClosureStereotypeDiagram extends BaseStereotypeDiagram {
	public ClosureAttrType closureAttrType;

	protected BaseRule rule;
	public BaseClosureStereotypeDiagram(BaseRule rule) {
		this.rule = rule;

		connectClosure();
	}

	protected abstract void connectClosure();
}
