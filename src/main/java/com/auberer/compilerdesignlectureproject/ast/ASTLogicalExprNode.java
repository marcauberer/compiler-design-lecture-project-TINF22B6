package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTLogicalExprNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitLogicalExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTCompareExprNode.getSelectionSet();
  }
}
