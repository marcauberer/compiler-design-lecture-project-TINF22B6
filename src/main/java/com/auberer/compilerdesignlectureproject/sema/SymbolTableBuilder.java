package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTFctDefNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVarDeclNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;

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
}
