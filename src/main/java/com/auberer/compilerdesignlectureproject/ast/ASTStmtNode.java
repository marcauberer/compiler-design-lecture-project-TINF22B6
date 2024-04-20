package com.auberer.compilerdesignlectureproject.ast;

public class ASTStmtNode extends ASTNode implements IVisitable {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitStmt(this);
  }
}
