package com.auberer.compilerdesignlectureproject.ast;

public interface IVisitable {
  <T> T accept(ASTVisitor<T> visitor);
}
