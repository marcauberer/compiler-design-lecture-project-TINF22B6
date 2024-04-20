package com.auberer.compilerdesignlectureproject.ast;

public class ASTTypeNode extends ASTNode implements IVisitable {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitType(this);
  }
}
