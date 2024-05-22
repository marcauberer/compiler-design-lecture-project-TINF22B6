package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;
import com.auberer.compilerdesignlectureproject.ast.ASTWhileLoopNode;

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
  public Void visitWhileLoop(ASTWhileLoopNode node){
    Scope whileLoopScope = new Scope( );
    whileLoopScope.parent = currentScopes.peek();
    currentScopes.push(whileLoopScope);
    visitChildren(node);
    currentScopes.pop();
    return null;
  }

}
