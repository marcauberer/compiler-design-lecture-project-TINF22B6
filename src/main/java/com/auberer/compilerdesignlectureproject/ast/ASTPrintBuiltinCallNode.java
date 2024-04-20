package com.auberer.compilerdesignlectureproject.ast;

public class ASTPrintBuiltinCallNode extends ASTNode implements IVisitable {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitPrintBuiltin(this);
  }
}
