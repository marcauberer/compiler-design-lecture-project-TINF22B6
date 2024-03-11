package com.auberer.compilerdesignlectureproject.ast;

public class ASTVisitor<T> {
  T visit(ASTNode node) {
    return node.accept(this);
  }

  T visitChildren(ASTNode node) {
    for (ASTNode child : node.getChildren())
      child.accept(this);
    return null;
  }

  T visitEntry(ASTEntryNode node) {
    return visitChildren(node);
  }
}
