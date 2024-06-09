package com.auberer.compilerdesignlectureproject.antlr;

import com.auberer.compilerdesignlectureproject.antlr.gen.TInfBaseVisitor;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ASTBuilder extends TInfBaseVisitor<ASTNode> {

  // Stack to keep track of the parent nodes
  Stack<ASTNode> parentStack = new Stack<>();

  @Override
  public ASTNode visitEntry(TInfParser.EntryContext ctx) {
    ASTEntryNode node = new ASTEntryNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitStmtLst(TInfParser.StmtLstContext ctx) {
    ASTStmtLstNode node = new ASTStmtLstNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitStmt(TInfParser.StmtContext ctx) {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitType(TInfParser.TypeContext ctx) {
    ASTTypeNode node = new ASTTypeNode();
    enterNode(node, ctx);

    if (ctx.TYPE_INT() != null) {
      node.setDataType(ASTTypeNode.DataType.INT);
    } else if (ctx.TYPE_DOUBLE() != null) {
      node.setDataType(ASTTypeNode.DataType.DOUBLE);
    } else if (ctx.TYPE_STRING() != null) {
      node.setDataType(ASTTypeNode.DataType.STRING);
    } else if (ctx.TYPE_BOOL() != null) {
      node.setDataType(ASTTypeNode.DataType.BOOL);
    } else if (ctx.TYPE_EMPTY() != null) {
      node.setDataType(ASTTypeNode.DataType.EMPTY);
    }

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitPrintBuiltinCall(TInfParser.PrintBuiltinCallContext ctx) {
    ASTPrintBuiltinCallNode node = new ASTPrintBuiltinCallNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitIfStmt(TInfParser.IfStmtContext ctx) {
    ASTIfStmtNode node = new ASTIfStmtNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitAfterIf(TInfParser.AfterIfContext ctx) {
    ASTAfterIfNode node = new ASTAfterIfNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitElsePre(TInfParser.ElsePreContext ctx) {
    ASTElsePreNode node = new ASTElsePreNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitElsePost(TInfParser.ElsePostContext ctx) {
    ASTElsePostNode node = new ASTElsePostNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    node.setExprType(ctx.ifStmt() != null ? ASTElsePostNode.ElseType.ELSE_IF : ASTElsePostNode.ElseType.ELSE);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitElse(TInfParser.ElseContext ctx) {
    ASTElseNode node = new ASTElseNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitWhileLoop(TInfParser.WhileLoopContext ctx) {
    ASTWhileLoopNode node = new ASTWhileLoopNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitDoWhileLoop(TInfParser.DoWhileLoopContext ctx) {
    ASTDoWhileLoopNode node = new ASTDoWhileLoopNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitForLoop(TInfParser.ForLoopContext ctx) {
    ASTForNode node = new ASTForNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitSwitchStmt(TInfParser.SwitchStmtContext ctx) {
    ASTSwitchStmtNode node = new ASTSwitchStmtNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);

    return node;
  }

  @Override
  public ASTNode visitCases(TInfParser.CasesContext ctx) {
    ASTCasesNode node = new ASTCasesNode();
    enterNode(node, ctx);

    int casesSize = ctx.CASE().size();
    node.setCasesSize(casesSize);

    List<String> cases = new ArrayList<>();

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode terminalNode){
        String caseLiteral = terminalNode.getSymbol().getText();
        cases.add(caseLiteral);
      }
    }

    node.setCases(cases);

    List<TerminalNode> doubles = ctx.DOUBLE_LIT();
    List<TerminalNode> ints = ctx.INT_LIT();
    List<TerminalNode> strings = ctx.STRING_LIT();

    for(TerminalNode ignored : doubles){
      node.addCaseType(ASTCasesNode.CaseType.DOUBLE_LIT);
    }
    for(TerminalNode ignored : ints) {
      node.addCaseType(ASTCasesNode.CaseType.INT_LIT);
    }
    for(TerminalNode ignored : strings){
      node.addCaseType(ASTCasesNode.CaseType.STRING_LIT);
    }


    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitDefault(TInfParser.DefaultContext ctx) {
    ASTDefaultNode node = new ASTDefaultNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitFctDef(TInfParser.FctDefContext ctx) {
    ASTFctDefNode node = new ASTFctDefNode();
    enterNode(node, ctx);

    node.setName(ctx.IDENTIFIER().toString());
    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitParamLst(TInfParser.ParamLstContext ctx) {
    ASTParamLstNode node = new ASTParamLstNode();
    enterNode(node, ctx);

    for (TInfParser.ParamContext context : ctx.param()) {
      visitParam(context);
    }
    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  public ASTNode visitParam(TInfParser.ParamContext ctx){
    ASTParamNode paramNode = new ASTParamNode();

    enterNode(paramNode, ctx);
    paramNode.setName(ctx.getText());
    visitChildren(ctx);
    exitNode(paramNode);
    return paramNode;
  }

  @Override
  public ASTNode visitLogic(TInfParser.LogicContext ctx) {
    ASTLogicNode node = new ASTLogicNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitFctCall(TInfParser.FctCallContext ctx) {
    ASTFctCallNode node = new ASTFctCallNode();
    enterNode(node, ctx);

    visitChildren(ctx);
    node.hasArgs(ctx.callParams() != null);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitCallParams(TInfParser.CallParamsContext ctx) {
    ASTFctCallNode node = new ASTFctCallNode();
    enterNode(node, ctx);

    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitVarDecl(TInfParser.VarDeclContext ctx) {
    ASTVarDeclNode node = new ASTVarDeclNode();
    enterNode(node, ctx);

    visitChildren(ctx);
    node.setVariableName(ctx.IDENTIFIER().toString());

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitAssignStmt(TInfParser.AssignStmtContext ctx) {
    ASTAssignStmtNode node = new ASTAssignStmtNode();
    enterNode(node, ctx);

    visitChildren(ctx);
    if (ctx.IDENTIFIER() != null)
      node.setVariableName(ctx.IDENTIFIER().toString());

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitLogicalExpr(TInfParser.LogicalExprContext ctx) {
    ASTLogicalExprNode node = new ASTLogicalExprNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode terminalNode){
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
    return node;
  }

  @Override
  public ASTNode visitCompareExpr(TInfParser.CompareExprContext ctx) {
    ASTCompareExprNode node = new ASTCompareExprNode();
    enterNode(node, ctx);

    if (ctx.EQUAL() != null) {
      node.setOperator(ASTCompareExprNode.CompareOperator.EQUAL);
    } else if (ctx.NOT_EQUAL() != null) {
      node.setOperator(ASTCompareExprNode.CompareOperator.NOT_EQUAL);
    }
    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitAdditiveExpr(TInfParser.AdditiveExprContext ctx) {
    ASTAdditiveExprNode node = new ASTAdditiveExprNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode terminalNode){
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
    return node;
  }

  @Override
  public ASTNode visitMultiplicativeExpr(TInfParser.MultiplicativeExprContext ctx) {
    ASTMultiplicativeExprNode node = new ASTMultiplicativeExprNode();
    enterNode(node, ctx);

    for (int i = 0; i < ctx.getChildCount(); i++){
      ParseTree child = ctx.getChild(i);
      if (child instanceof TerminalNode terminalNode){
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
    return node;
  }

  @Override
  public ASTNode visitPrefixExpr(TInfParser.PrefixExprContext ctx) {
    ASTPrefixExprNode node = new ASTPrefixExprNode();
    enterNode(node, ctx);

    if (ctx.PLUS() != null) {
      node.setOperator(ASTPrefixExprNode.PrefixOperator.PLUS);
    } else if (ctx.MINUS() != null) {
      node.setOperator(ASTPrefixExprNode.PrefixOperator.MINUS);
    }
    visitChildren(ctx);

    exitNode(node);
    return node;
  }

  @Override
  public ASTNode visitAtomicExpr(TInfParser.AtomicExprContext ctx) {
    ASTAtomicExprNode node = new ASTAtomicExprNode();
    enterNode(node, ctx);

    if (ctx.INT_LIT() != null){
      node.setExprType(ASTAtomicExprNode.AtomicType.INT_LIT);
      node.setIntLit(Integer.parseInt(ctx.STRING_LIT().toString()));
    } else if (ctx.DOUBLE_LIT() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.DOUBLE_LIT);
      node.setDoubleLit(Double.parseDouble(ctx.STRING_LIT().toString()));
    } else if (ctx.STRING_LIT() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.STRING_LIT);
      node.setStringLit(ctx.STRING_LIT().toString());
    } else if (ctx.IDENTIFIER() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.IDENTIFIER);
      node.setIdentifier(ctx.STRING_LIT().toString());
    } else if (ctx.fctCall() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.FCT_CALL);
      visit(ctx.fctCall());
    } else if (ctx.printBuiltinCall() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.PRINT_BUILTIN_CALL);
      visit(ctx.printBuiltinCall());
    } else if (ctx.logicalExpr() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.LOGICAL_EXPR);
      visit(ctx.logicalExpr());
    } else if (ctx.FALSE() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.BOOL_LIT);
      node.setBoolLit(false);
    } else if (ctx.TRUE() != null) {
      node.setExprType(ASTAtomicExprNode.AtomicType.BOOL_LIT);
      node.setBoolLit(true);
    }

    exitNode(node);
    return node;
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
