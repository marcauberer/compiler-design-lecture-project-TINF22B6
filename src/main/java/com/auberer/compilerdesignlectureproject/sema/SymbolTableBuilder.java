package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVarDeclNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;

import java.util.Stack;

public class SymbolTableBuilder extends ASTVisitor<Void> {

  Stack<Scope> currentScopes = new Stack<>();

  @Override
  public Void visitEntry(ASTEntryNode node) {
    Scope rootScope = new Scope();
    currentScopes.push(rootScope);

    visitChildren(node);

    // Check if main function is present
    if (rootScope.lookupSymbol("main") == null)
      throw new SemaError("No main function found");

    assert currentScopes.size() == 1 && currentScopes.peek() == rootScope;
    currentScopes.pop();
    return null;
  }
}
