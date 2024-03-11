package com.auberer.compilerdesignlectureproject.ast;

public class ASTEntryNode extends ASTNode implements IVisitable {

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitEntry(this);
  }
}
