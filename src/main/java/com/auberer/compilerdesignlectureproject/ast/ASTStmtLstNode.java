package com.auberer.compilerdesignlectureproject.ast;

public class ASTStmtLstNode extends ASTNode implements IVisitable {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitStmtLst(this);
  }
}
