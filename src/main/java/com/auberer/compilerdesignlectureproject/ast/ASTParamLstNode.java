package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTParamLstNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitParamLst(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTTypeNode.getSelectionSet();
  }
}
