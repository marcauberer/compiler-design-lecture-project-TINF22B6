package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTIfStmtNode extends ASTNode {

  public static Set<TokenType> getSelectionSet() {

    return Set.of(TokenType.TOK_IF);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitIf(this);
  }
}
