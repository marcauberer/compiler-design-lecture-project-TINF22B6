package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTEPost extends ASTNode {
  public static Set<TokenType> getSelectionSet() {

    Set<TokenType> selectionSet = new HashSet<>();
    selectionSet.addAll(ASTIFStmtNode.getSelectionSet());
    selectionSet.addAll(ASTElse.getSelectionSet());

    return selectionSet;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitEPost(this);
  }

}
