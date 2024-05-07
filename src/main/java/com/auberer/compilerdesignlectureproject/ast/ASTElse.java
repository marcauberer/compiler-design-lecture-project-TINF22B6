package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTElse extends ASTNode {
  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_LBRACE);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitElse(this);
  }
}
