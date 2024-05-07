package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTIFStmtNode extends ASTNode {
  private static ILexer lexer;

  public static Set<TokenType> getSelectionSet() {

    return Set.of(TokenType.TOK_IF);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitIf(this);
  }
}
