package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTAfterIfNode extends ASTNode {
  public static Set<TokenType> getSelectionSet() {
    return ASTElsePreNode.getSelectionSet();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitAfterIf(this);
  }
}

