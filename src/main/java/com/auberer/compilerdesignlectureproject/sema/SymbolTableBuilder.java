package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;

import java.util.Stack;

public class SymbolTableBuilder extends ASTVisitor<Void> {

  Stack<Scope> currentScopes = new Stack<>();

  public SymbolTableBuilder() {
    assert currentScopes.empty();
    currentScopes.push(new Scope());
  }

  @Override
  public Void visitEntry(ASTEntryNode node) {
    visitChildren(node);

    // Check if main function is present
    if (currentScopes.peek().lookupSymbol("main") == null)
      throw new SemaError("No main function found");

    return null;
  }

  @Override
  public Void visitSwitchStmt(ASTSwitchStmtNode node) {
    Scope scope = new Scope();
    currentScopes.push(scope);

    visitChildren(node);

    assert currentScopes.peek() == scope;
    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitCases(ASTCasesNode node) {
    Scope scope = new Scope();
    currentScopes.push(scope);

    visitChildren(node);

    assert currentScopes.peek() == scope;
    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitDefault(ASTDefaultNode node) {
    Scope scope = new Scope();
    currentScopes.push(scope);

    visitChildren(node);

    assert currentScopes.peek() == scope;
    currentScopes.pop();
    return null;
  }
}
