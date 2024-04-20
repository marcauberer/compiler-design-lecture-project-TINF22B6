package com.auberer.compilerdesignlectureproject.ast;

public class ASTPrintBuiltinCallNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitPrintBuiltin(this);
  }
}
