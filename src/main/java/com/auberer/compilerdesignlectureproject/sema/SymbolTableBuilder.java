package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;

public class SymbolTableBuilder extends ASTVisitor<Void> {

  @Override
  public Void visitEntry(ASTEntryNode node) {
    // ToDo: Implement SemaError and this method
    // Current scope to root scope
    // Visit children
    // Check if main function exists
    return null;
  }
}
