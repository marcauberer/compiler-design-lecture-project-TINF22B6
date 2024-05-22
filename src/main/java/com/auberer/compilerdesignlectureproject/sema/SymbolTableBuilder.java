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
  public Void visitVarDecl(ASTVarDeclNode node) {
    visitChildren(node);

    if (currentScopes.peek().lookupSymbolStrict(node.getVariableName()) ==null) {
      currentScopes.peek().insertSymbol(node.getVariableName(), node);
    } else {
      throw new SemaError("Scope already exists");
    }

    return null;
  }

  public Void visitAssignStmt(ASTAssignStmtNode node) {
    visitChildren(node);

    if (currentScopes.peek().lookupSymbolStrict(node.getVariableName()) ==null) {
      currentScopes.peek().insertSymbol(node.getVariableName(), node);
    } else {
      throw new SemaError("Scope already exists");
    }
    return null;
  }
}
