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
  public Void visitIf(ASTIfStmtNode node) {
    Scope ifScope = new Scope();
    currentScopes.push(ifScope);

    visitChildren(node);

    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitElse(ASTElseNode node) {
    Scope elseScope = new Scope();
    currentScopes.push(elseScope);

    visitChildren(node);

    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitDoWhileLoop(ASTDoWhileLoopNode node) {
    Scope doWhileScope = new Scope();
    currentScopes.push(doWhileScope);

    visitChildren(node);

    assert currentScopes.size() >= 2 && currentScopes.peek() == doWhileScope;
    currentScopes.pop();
    return null;
  }
}
