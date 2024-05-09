package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTCompareExprNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitCompareExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTPrefixExprNode.getSelectionSet();
  }
}
