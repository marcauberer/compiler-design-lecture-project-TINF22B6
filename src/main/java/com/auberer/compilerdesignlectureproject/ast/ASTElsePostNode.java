package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTElsePostNode extends ASTNode {
  public static Set<TokenType> getSelectionSet() {

    Set<TokenType> selectionSet = new HashSet<>();
    selectionSet.addAll(ASTIfStmtNode.getSelectionSet());
    selectionSet.addAll(ASTElseNode.getSelectionSet());

    return selectionSet;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitEPost(this);
  }

}
