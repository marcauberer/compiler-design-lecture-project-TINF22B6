package com.auberer.compilerdesignlectureproject.antlr;

import com.auberer.compilerdesignlectureproject.antlr.gen.TInfBaseVisitor;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.ast.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
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
    return super.visitDoWhileLoop(ctx);
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
  public Void visitLogicalExpr(TInfParser.LogicalExprContext ctx) { //enter node -- liste erstellen für operatoren -- loop über alle subnodes von ctx -- operatoren speichen und über operanden drüber gehen -- kinder besuchen -- exit node
    List<ParseTree> operatorsList = new ArrayList<>();
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        operatorsList.add(child);
      } else if (child instanceof ParserRuleContext) {
        visit(child);
      }
    }


    exitNode(node);

    return null;

  }
  @Override
  public Void visitCompareExpr(TInfParser.CompareExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    List<ParseTree> operatorsList = new ArrayList<>();

    enterNode(node);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        operatorsList.add(child);
      } else if (child instanceof ParserRuleContext) {
        visit(child);
      }
    }

    exitNode(node);

    return null;

  }

  @Override
  public Void visitAdditiveExpr(TInfParser.AdditiveExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    List<ParseTree> operatorsList = new ArrayList<>();

    enterNode(node);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        operatorsList.add(child);
      } else if (child instanceof ParserRuleContext) {
        visit(child);
      }
    }
    exitNode(node);

    return null;

  }

  @Override
  public Void visitMultiplicativeExpr(TInfParser.MultiplicativeExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    List<ParseTree> operatorsList = new ArrayList<>();

    enterNode(node);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        operatorsList.add(child);
      } else if (child instanceof ParserRuleContext) {
        visit(child);
      }
    }


    exitNode(node);

    return null;
  }

  @Override
  public Void visitPrefixExpr(TInfParser.PrefixExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    TerminalNode terminalNode;
    enterNode(node);

    if (ctx.equals(ctx.PLUS())) {
        terminalNode = ctx.PLUS();
    }
    else if (ctx.equals(ctx.MINUS())) {
      terminalNode = ctx.MINUS();
    }

    exitNode(node);

    return null;
  }

  @Override
  public Void visitAtomicExpr(TInfParser.AtomicExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();

    enterNode(node);

    exitNode(node);
    return null;
  } //was kam an? entsprechend values speichern (felder im knoten anlegen)

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
