// Generated from C:/IdeaProjects/compiler-design-lecture-project-TINF22B6/Example.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExampleParser}.
 */
public interface ExampleListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExampleParser#addition}.
	 * @param ctx the parse tree
	 */
	void enterAddition(ExampleParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExampleParser#addition}.
	 * @param ctx the parse tree
	 */
	void exitAddition(ExampleParser.AdditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExampleParser#const}.
	 * @param ctx the parse tree
	 */
	void enterConst(ExampleParser.ConstContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExampleParser#const}.
	 * @param ctx the parse tree
	 */
	void exitConst(ExampleParser.ConstContext ctx);
}