package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTPrefixExprNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitPrefixExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    Set<TokenType> selectionSet = new HashSet<>();
    selectionSet.add(TokenType.TOK_PLUS);
    selectionSet.add(TokenType.TOK_MINUS);
    selectionSet.addAll(ASTAtomicExprNode.getSelectionSet());
    return selectionSet;
  }
}
