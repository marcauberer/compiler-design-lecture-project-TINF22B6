package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;

public class SymbolTableBuilder extends ASTVisitor<Void> {

  @Override
  public Void visitEntry(ASTEntryNode node) {
    return null;
  }
}
