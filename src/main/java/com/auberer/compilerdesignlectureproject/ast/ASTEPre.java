package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTEPre extends ASTNode {

  public static Set<TokenType> getSelectionSet() {

    return Set.of(TokenType.TOK_ELSE);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitEPre(this);
  }
}
