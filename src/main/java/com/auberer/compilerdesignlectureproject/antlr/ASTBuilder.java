package com.auberer.compilerdesignlectureproject.antlr;

import com.auberer.compilerdesignlectureproject.antlr.gen.TInfBaseVisitor;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class ASTBuilder extends TInfBaseVisitor<Void> {

  // Stack to keep track of the parent nodes
  Stack<ASTNode> parentStack = new Stack<>();

  @Override
  public Void visitEntry(TInfParser.EntryContext ctx) {
    ASTEntryNode node = new ASTEntryNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitStmtLst(TInfParser.StmtLstContext ctx) {
    ASTStmtLstNode node = new ASTStmtLstNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitStmt(TInfParser.StmtContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitType(TInfParser.TypeContext ctx) {
    ASTTypeNode node = new ASTTypeNode();
    enterNode(node, ctx);

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
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitIfStmt(TInfParser.IfStmtContext ctx) {
    ASTIfStmtNode node = new ASTIfStmtNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitAfterIf(TInfParser.AfterIfContext ctx) {
    ASTAfterIfNode node = new ASTAfterIfNode();
    enterNode(node);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitElse(TInfParser.ElseContext ctx) {
    ASTElseNode node = new ASTElseNode();
    enterNode(node);

    if (node.getParent() instanceof ASTIfStmtNode) {
      node.setType(DataType.ELSE_IF);
    } else {
      node.setType(DataType.ELSE);
    }

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitWhileLoop(TInfParser.WhileLoopContext ctx) {
    ASTWhileLoopNode node = new ASTWhileLoopNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitDoWhileLoop(TInfParser.DoWhileLoopContext ctx) {
    ASTDoWhileLoopNode node = new ASTDoWhileLoopNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitForLoop(TInfParser.ForLoopContext ctx) {
    ASTForNode node = new ASTForNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
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
    ASTFctDefNode node = new ASTFctDefNode();
    enterNode(node, ctx);

    node.setName(ctx.IDENTIFIER().toString());
    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitParamLst(TInfParser.ParamLstContext ctx) {
    ASTParamLstNode node = new ASTParamLstNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.IDENTIFIER().size(); i++) {
      node.addParamName(ctx.IDENTIFIER(i).toString());
    }
    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitLogic(TInfParser.LogicContext ctx) {
    ASTLogicNode node = new ASTLogicNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitFctCall(TInfParser.FctCallContext ctx) {
    ASTFctCallNode node = new ASTFctCallNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitCallParams(TInfParser.CallParamsContext ctx) {
    ASTFctCallNode node = new ASTFctCallNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return null;
  }

  @Override
  public Void visitVarDecl(TInfParser.VarDeclContext ctx) {
    ASTVarDeclNode node = new ASTVarDeclNode();
    enterNode(node, ctx);

    visitChildren(ctx);
    node.setVariableName(ctx.IDENTIFIER().toString());

    exitNode(node);
    return null;
  }

  @Override
  public Void visitAssignExpr(TInfParser.AssignExprContext ctx) {
    ASTAssignExprNode node = new ASTAssignExprNode();
    enterNode(node, ctx);

    visitChildren(ctx);
    node.setVariableName(ctx.IDENTIFIER().toString());

    exitNode(node);
    return null;
  }

  @Override
  public Void visitLogicalExpr(TInfParser.LogicalExprContext ctx) {
    ASTLogicalExprNode node = new ASTLogicalExprNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        if (token.getType() == TInfParser.LOGICAL_AND) {
          node.operatorsListAdd(ASTLogicalExprNode.LogicalOperator.AND);
        } else if (token.getType() == TInfParser.LOGICAL_OR) {
          node.operatorsListAdd(ASTLogicalExprNode.LogicalOperator.OR);
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
    ASTCompareExprNode node = new ASTCompareExprNode();
    enterNode(node, ctx);

    if (ctx.EQUAL() != null) {
      node.setOperator(ASTCompareExprNode.CompareOperator.EQUAL);
    } else if (ctx.NOT_EQUAL() != null) {
      node.setOperator(ASTCompareExprNode.CompareOperator.NOT_EQUAL);
    }

    exitNode(node);
    return null;
  }

  @Override
  public Void visitAdditiveExpr(TInfParser.AdditiveExprContext ctx) {
    ASTAdditiveExprNode node = new ASTAdditiveExprNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        if (token.getType() == TInfParser.PLUS){
          node.operatorsListAdd(ASTAdditiveExprNode.AdditiveOperator.PLUS);
        }
        else if (token.getType() == TInfParser.MINUS){
          node.operatorsListAdd(ASTAdditiveExprNode.AdditiveOperator.MINUS);
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
    ASTMultiplicativeExprNode node = new ASTMultiplicativeExprNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode){
        TerminalNode terminalNode = (TerminalNode) child;
        Token token = terminalNode.getSymbol();
        if (token.getType() == TInfParser.MUL){
          node.operatorsListAdd(ASTMultiplicativeExprNode.MultiplicativeOperator.MUL);
        }
        else if (token.getType() == TInfParser.DIV){
          node.operatorsListAdd(ASTMultiplicativeExprNode.MultiplicativeOperator.DIV);
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
    ASTPrefixExprNode node = new ASTPrefixExprNode();
    enterNode(node, ctx);

    if (ctx.PLUS() != null) {
      node.setOperator(ASTPrefixExprNode.PrefixOperator.PLUS);
    } else if (ctx.MINUS() != null) {
      node.setOperator(ASTPrefixExprNode.PrefixOperator.MINUS);
    }

    exitNode(node);
    return null;
  }

  @Override
  public Void visitAtomicExpr(TInfParser.AtomicExprContext ctx) {
    ASTAtomicExprNode node = new ASTAtomicExprNode();
    enterNode(node, ctx);

    if (ctx.INT_LIT() != null){
      node.setOperator(ASTAtomicExprNode.AtomicOperator.INT_LIT);
      node.setIntLit(Integer.parseInt(ctx.STRING_LIT().toString()));
    } else if (ctx.DOUBLE_LIT() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.DOUBLE_LIT);
      node.setDoubleLit(Double.parseDouble(ctx.STRING_LIT().toString()));
    } else if (ctx.STRING_LIT() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.STRING_LIT);
      node.setStringLit(ctx.STRING_LIT().toString());
    } else if (ctx.IDENTIFIER() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.IDENTIFIER);
      node.setIdentifier(ctx.STRING_LIT().toString());
    } else if (ctx.fctCall() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.FCT_CALL);
      visit(ctx.fctCall());
    } else if (ctx.printBuiltinCall() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.PRINT_BUILTIN_CALL);
      visit(ctx.printBuiltinCall());
    } else if (ctx.assignExpr() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.ASSIGN_EXPR);
      visit(ctx.assignExpr());
    } else if (ctx.FALSE() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.BOOL_LIT);
      node.setBoolLit(false);
    } else if (ctx.TRUE() != null) {
      node.setOperator(ASTAtomicExprNode.AtomicOperator.BOOL_LIT);
      node.setBoolLit(true);
    }

    exitNode(node);
    return null;
  }

  private void enterNode(ASTNode node, ParserRuleContext ctx) {
    // Attach CodeLoc to AST node
    CodeLoc codeLoc = new CodeLoc(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    node.setCodeLoc(codeLoc);

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
