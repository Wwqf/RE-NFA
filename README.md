## Version 1.0



### 支持语法

|           表达式           |               匹配               |        例子         |
| :------------------------: | :------------------------------: | :-----------------: |
|             c              |         单个非运算字符c          |       a, b, z       |
|             \c             |          字符c的字面值           |         \\\         |
|            [s]             |     字符串s中的任意一个字符      | [abc], [a-z], [0-9] |
|            [^s]            |   不在字符串s中的任意一个字符    |       [^abc]        |
| r<sub>1</sub>r<sub>2</sub> | r<sub>1</sub>后加上r<sub>2</sub> |         abc         |
|            (r)             |             与r相同              |        (ab)         |
|             r*             |  和r匹配的零个或多个串连接的串   |     a*, [abc]\*     |
|             r+             |  和r匹配的一个或多个串连接的串   |     a+, [0-9]+      |
|             r?             |           零个或一个r            |      a?, (ab)?      |

注解1: c匹配的是非元标识字符, 即除(\ " . ^ ...) 代表特定意义的普通字符。 \c 匹配的是元标识字符的字面值, 即 '.' 匹配除换行符以外的字符, 加上 '\\.' 表示匹配一个小数点 '.' 。

注解2: r*口语为kleene闭包(克林闭包)，r+口语为正闭包，r?口语为?????(我也不知道)。



### 规则与定式

​		**有限状态自动机是一个五元组**
$$
M=(Q, Σ, δ, q0, F)
$$


​		Q		状态的非空有穷集合。∀q∈Q，q称为M的一个状态。

​		Σ		输入字母表。

​		δ		状态转移函数，有时又叫作状态[转换函数](https://baike.baidu.com/item/转换函数)或者移动函数，δ：Q×Σ→Q，δ(q,a)=p。

​		q0	  M的开始状态，也可叫作初始状态或启动状态。q0∈Q。

​		F		M的终止状态集合。F被Q包含。任给q∈F，q称为M的终止状态。		



#### 规则

​	定义： 规则 R∈Σ，可以从当前状态通过R到达新的状态。

​	![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/1.png)	`图一`

​	例如`图一`，0号状态可以通过**单字符规则**（CharacterRule）到达1号状态。

​	 	![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/2.png)	`图二`

​	在`图二`中，0号状态可以通过**或规则**（OrRule），即0~9中任意一个字符可以到达1号状态。

​	`图二`也是`图三`的简化版（防止状态数过多）：

​	![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/3.png)		`图三`

​	在代码中，共有七个实际规则：

		1. 单字符规则（Character Rule），通过字符C可由当前状态到新的状态。
  		2. 字符串（String Rule），一个单字符规则序列；
  		3. 范围规则（Range Rule），即连词符规则，连词符是类似`[a-z]`、`[0-9]`中的`-`符号，**表示a<sub>1</sub>,a<sub>2</sub>,a<sub>3</sub>...a<sub>n</sub>,形成一个逻辑上连续的序列时，可以表示为a<sub>1</sub>-a<sub>n</sub>。**
  		4. 或规则（Or Rule），包含一个规则序列，只要匹配其中任意一个即可到达新的状态。
  		5. 计数规则 （Counting Rule）正则式 r{m, n}的规则实例。
  		6. 闭包规则 （Closure Rule）由一个基础规则和一个闭包属性组成。
  		7. 组合规则（Combination Rule）是一个规则序列，匹配时需要按照顺序匹配。



​	在其中，字符串规则不是必要规则，只是为了简化状态数所用规则。（在用**连接定式**时，两个正则式之间需要插入一条空转换函数，如果类似 `double -> double` 这条正则式，每个字符构成的单字符规则，在连接时需要插入五个空转换函数，将多出五个无用状态。）

![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/4.png)

​	在类图中，还有三个类：

​		(1). Base Rule，是所有规则的基类。

​		(2). Simple Rule，是简单规则的基类，所有继承此类的类在生成有限状态自动机时将重新生成。

​		(3). Complicated Rule，是复杂规则的基类，所有继承此类的类在生成有限状态自动机时会返回之前生成过的	自动机的拷贝。

​	

#### 定式

​	为了生成有限状态自动机的方便，每个规则可以生成一个**模板**有限状态自动机，这样的模板自动机称为**定式**。由规则生成的有限状态自动机，此自动机包含一个开始状态和一个接受状态，不用考虑在开始状态和接受状态之间有多少转换。

​		`图四`的正则式为: r -> a，构建一个**单定式**有限状态自动机，0号状态即Start State，1号状态即Accept State。

​		![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/5.png)	`图四`

​		为`图四`构建一个Kleene闭包，即 ***r*** * 。

​	![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/6.png)	`图五`

​		

​	为了防止生成错乱，原本每个定式的构造都是传入Base Rule，后来强制规定哪个规则可以传入哪个定式构造函数。
![](https://github.com/Wwqf/RE-NFA/tree/version1.1/src/data/image/7.png)

​	

​	在类图中，每种定式代表一种正则表达式的规则。

		1. Kleene Closure State 构建 r*
  		2. Zero One Closure State 构建 r?
  		3. Positive Closure State 构建 r+
  		4.  Or Stereotype State 构建 [a-z]
  		5. Single Stereotype State 构建 c, \c, 0-9(`图二`范围规则也是一条**单定式**)
  		6. Connect Stereotype State 构建 r<sub>1</sub>r<sub>2</sub>
