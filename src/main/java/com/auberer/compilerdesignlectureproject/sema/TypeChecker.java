package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;

import java.util.Stack;

public class TypeChecker extends ASTVisitor<ExprResult> {

  Stack<Scope> currentScopes = new Stack<>();

  public TypeChecker() {
    assert currentScopes.empty();
  }

  @Override
  public ExprResult visitPrintBuiltin(ASTPrintBuiltinCallNode node) {
    // Visit the children of the node
    visitChildren(node);

    // ToDo: Insert check if logicalExpr is int, double or string

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitSwitchStmt(ASTSwitchStmtNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult result = visit(logicalExprNode);
    if(!result.getType().isOneOf(SuperType.TY_INT, SuperType.TY_DOUBLE, SuperType.TY_STRING)){
      throw new SemaError(node, "Switch statement expects int, double or string, but got '" + result.getType().toString() + "'");
    }

    node.getCases().setExpectedType(result.getType());

    visit(node.getCases());
    visit(node.getDefault());

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitCases(ASTCasesNode node) {
    node.getExpectedType();

    return super.visitCases(node);
  }

  @Override
  public ExprResult visitDefault(ASTDefaultNode node) {
    visitChildren(node);

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }
}
