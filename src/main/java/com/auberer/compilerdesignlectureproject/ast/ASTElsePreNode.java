package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTElsePreNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitElsePre(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_ELSE);
  }
}
