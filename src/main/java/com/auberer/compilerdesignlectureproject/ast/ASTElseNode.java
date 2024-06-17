package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTElseNode extends ASTNode {
  
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitElse(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_LBRACE);
  }

  public ASTStmtLstNode getStmtLstNode() {
    return getChild(ASTStmtLstNode.class, 0);
  }
}
