package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTPrintBuiltinCallNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;

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
}
