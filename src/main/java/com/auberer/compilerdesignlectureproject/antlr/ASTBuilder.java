package com.auberer.compilerdesignlectureproject.antlr;

import com.auberer.compilerdesignlectureproject.antlr.gen.TInfBaseVisitor;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.ast.*;

import java.util.Stack;

public class ASTBuilder extends TInfBaseVisitor<Void> {

  // Stack to keep track of the parent nodes
  Stack<ASTNode> parentStack = new Stack<>();

  @Override
  public Void visitEntry(TInfParser.EntryContext ctx) {
    ASTEntryNode node = new ASTEntryNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitStmtLst(TInfParser.StmtLstContext ctx) {
    ASTStmtLstNode node = new ASTStmtLstNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitStmt(TInfParser.StmtContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitType(TInfParser.TypeContext ctx) {
    ASTTypeNode node = new ASTTypeNode();
    enterNode(node);

    if (ctx.TYPE_INT() != null) {
      node.setType(ASTTypeNode.DataType.INT);
    } else if (ctx.TYPE_DOUBLE() != null) {
      node.setType(ASTTypeNode.DataType.DOUBLE);
    } else if (ctx.TYPE_STRING() != null) {
      node.setType(ASTTypeNode.DataType.STRING);
    } else if (ctx.TYPE_EMPTY() != null) {
      node.setType(ASTTypeNode.DataType.EMPTY);
    }

    exitNode(node);
    return null;
  }

  @Override
  public Void visitPrintBuiltinCall(TInfParser.PrintBuiltinCallContext ctx) {
    ASTPrintBuiltinCallNode node = new ASTPrintBuiltinCallNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitIfStmt(TInfParser.IfStmtContext ctx) {
    return super.visitIfStmt(ctx);
  }

  @Override
  public Void visitAfterIf(TInfParser.AfterIfContext ctx) {
    return super.visitAfterIf(ctx);
  }

  @Override
  public Void visitElse(TInfParser.ElseContext ctx) {
    return super.visitElse(ctx);
  }

  @Override
  public Void visitWhileLoop(TInfParser.WhileLoopContext ctx) {
    return super.visitWhileLoop(ctx);
  }

  @Override
  public Void visitDoWhileLoop(TInfParser.DoWhileLoopContext ctx) {
    ASTDoWhileLoopNode node = new ASTDoWhileLoopNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitForLoop(TInfParser.ForLoopContext ctx) {
    return super.visitForLoop(ctx);
  }

  @Override
  public Void visitSwitchStmt(TInfParser.SwitchStmtContext ctx) {
    return super.visitSwitchStmt(ctx);
  }

  @Override
  public Void visitCases(TInfParser.CasesContext ctx) {
    return super.visitCases(ctx);
  }

  @Override
  public Void visitDefault(TInfParser.DefaultContext ctx) {
    return super.visitDefault(ctx);
  }

  @Override
  public Void visitFctDef(TInfParser.FctDefContext ctx) {
    return super.visitFctDef(ctx);
  }

  @Override
  public Void visitParamLst(TInfParser.ParamLstContext ctx) {
    return super.visitParamLst(ctx);
  }

  @Override
  public Void visitLogic(TInfParser.LogicContext ctx) {
    return super.visitLogic(ctx);
  }

  @Override
  public Void visitFctCall(TInfParser.FctCallContext ctx) {
    return super.visitFctCall(ctx);
  }

  @Override
  public Void visitCallParams(TInfParser.CallParamsContext ctx) {
    return super.visitCallParams(ctx);
  }

  @Override
  public Void visitVarDecl(TInfParser.VarDeclContext ctx) {
    return super.visitVarDecl(ctx);
  }

  @Override
  public Void visitAssignExpr(TInfParser.AssignExprContext ctx) {
    return super.visitAssignExpr(ctx);
  }

  @Override
  public Void visitLogicalExpr(TInfParser.LogicalExprContext ctx) {
    return super.visitLogicalExpr(ctx);
  }

  @Override
  public Void visitCompareExpr(TInfParser.CompareExprContext ctx) {
    return super.visitCompareExpr(ctx);
  }

  @Override
  public Void visitAdditiveExpr(TInfParser.AdditiveExprContext ctx) {
    return super.visitAdditiveExpr(ctx);
  }

  @Override
  public Void visitMultiplicativeExpr(TInfParser.MultiplicativeExprContext ctx) {
    return super.visitMultiplicativeExpr(ctx);
  }

  @Override
  public Void visitPrefixExpr(TInfParser.PrefixExprContext ctx) {
    return super.visitPrefixExpr(ctx);
  }

  @Override
  public Void visitAtomicExpr(TInfParser.AtomicExprContext ctx) {
    return super.visitAtomicExpr(ctx);
  }

  private void enterNode(ASTNode node) {
    if (!parentStack.isEmpty()) {
      // Make sure the node is not pushed twice
      assert parentStack.peek() != node;
      // Link parent and child nodes, so we can traverse the tree
      ASTNode parent = parentStack.peek();
      parent.addChild(node);
      node.setParent(parent);
    }
    // Push the node to the stack
    parentStack.push(node);
  }

  private void exitNode(ASTNode node) {
    // Make sure the node is the last one pushed
    assert parentStack.peek() == node;
    // Remove the node from the stack
    parentStack.pop();
  }
}
