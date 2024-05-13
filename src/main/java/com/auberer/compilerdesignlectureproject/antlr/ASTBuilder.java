package com.auberer.compilerdesignlectureproject.antlr;

import com.auberer.compilerdesignlectureproject.antlr.gen.TInfBaseVisitor;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.parser.Parser;
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
  public Void visitLogicalExpr(TInfParser.LogicalExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        if (token.getType() == TInfParser.LOGICAL_AND){
          ASTLogicalExprNode.operatorsListAdd(ASTLogicalExprNode.LogicalOperator.AND);
        }
        else if (token.getType() == TInfParser.LOGICAL_OR){
          ASTLogicalExprNode.operatorsListAdd(ASTLogicalExprNode.LogicalOperator.OR);
        }
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

    enterNode(node);
    if (ctx.EQUAL() != null){
      ASTCompareExprNode.setOperator(ASTCompareExprNode.CompareOperator.EQUAL);
    }
    else if (ctx.NOT_EQUAL() != null){
      ASTCompareExprNode.setOperator(ASTCompareExprNode.CompareOperator.NOT_EQUAL);
    }

    exitNode(node);

    return null;

  }

  @Override
  public Void visitAdditiveExpr(TInfParser.AdditiveExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();

    enterNode(node);
    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        if (token.getType() == TInfParser.PLUS){
          ASTAdditiveExprNode.operatorsListAdd(ASTAdditiveExprNode.AdditiveOperator.PLUS);
        }
        else if (token.getType() == TInfParser.MINUS){
          ASTAdditiveExprNode.operatorsListAdd(ASTAdditiveExprNode.AdditiveOperator.MINUS);
        }
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

    enterNode(node);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        if (token.getType() == TInfParser.MUL){
          ASTMultiplicativeExprNode.operatorsListAdd(ASTMultiplicativeExprNode.MultiplicativeOperator.MUL);
        }
        else if (token.getType() == TInfParser.DIV){
          ASTMultiplicativeExprNode.operatorsListAdd(ASTMultiplicativeExprNode.MultiplicativeOperator.DIV);
        }
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

    enterNode(node);

    if (ctx.PLUS() != null) {
        ASTPrefixExprNode.setOperator(ASTPrefixExprNode.PrefixOperator.PLUS);
    }
    else if (ctx.MINUS() != null) {
      ASTPrefixExprNode.setOperator(ASTPrefixExprNode.PrefixOperator.MINUS);
    }

    exitNode(node);

    return null;
  }

  @Override
  public Void visitAtomicExpr(TInfParser.AtomicExprContext ctx) {
    ASTStmtNode node = new ASTStmtNode();

    enterNode(node);

    if (ctx.INT_LIT() != null){
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.INT_LIT);
      ASTAtomicExprNode.setInt_lit(Integer.valueOf(ctx.STRING_LIT().toString()));
    }
    else if (ctx.DOUBLE_LIT() != null){
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.DOUBLE_LIT);
      ASTAtomicExprNode.setDouble_lit(Double.parseDouble(ctx.STRING_LIT().toString()));
    }
    else if (ctx.STRING_LIT() != null){
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.STRING_LIT);
      ASTAtomicExprNode.setString_lit(ctx.STRING_LIT().toString());
    }
    else if (ctx.IDENTIFIER() != null) {
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.IDENTIFIER);
      ASTAtomicExprNode.setIdentifier(ctx.STRING_LIT().toString());
    }
    else if (ctx.fctCall() != null) {
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.FCT_CALL);
      Parser parser = new Parser();
      parser.parseFctCall();
    }
    else if (ctx.printBuiltinCall() != null) {
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.PRINT_BUILT_IN_CALL);
      Parser parser = new Parser();
      parser.parsePrintBuiltinCall();
    }
    else if (ctx.assignExpr() != null) {
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.ASSIGN_EXPR);
      Parser parser = new Parser();
      parser.parseAssignExpr();
    }
    else if (ctx.FALSE() != null) {
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.FALSE);
      ASTAtomicExprNode.setTrue_or_false(false);
    }
    else if (ctx.TRUE() != null) {
      ASTAtomicExprNode.setOperator(ASTAtomicExprNode.AtomicOperator.TRUE);
      ASTAtomicExprNode.setTrue_or_false(true);
    }

    exitNode(node);
    return null;
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
