// Generated from TInf.g4 by ANTLR 4.13.1
package com.auberer.compilerdesignlectureproject.antlr.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TInfParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TInfVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TInfParser#entry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntry(TInfParser.EntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#stmtLst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtLst(TInfParser.StmtLstContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(TInfParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(TInfParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#printBuiltinCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintBuiltinCall(TInfParser.PrintBuiltinCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(TInfParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#afterIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAfterIf(TInfParser.AfterIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#elsePre}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsePre(TInfParser.ElsePreContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#elsePost}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsePost(TInfParser.ElsePostContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse(TInfParser.ElseContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(TInfParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#doWhileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileLoop(TInfParser.DoWhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(TInfParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#switchStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStmt(TInfParser.SwitchStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#cases}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCases(TInfParser.CasesContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#default}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefault(TInfParser.DefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#fctDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFctDef(TInfParser.FctDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#paramLst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamLst(TInfParser.ParamLstContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#logic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic(TInfParser.LogicContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#fctCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFctCall(TInfParser.FctCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#callParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallParams(TInfParser.CallParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(TInfParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#assignExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(TInfParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#logicalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpr(TInfParser.LogicalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#compareExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpr(TInfParser.CompareExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(TInfParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(TInfParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#prefixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(TInfParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TInfParser#atomicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomicExpr(TInfParser.AtomicExprContext ctx);
}