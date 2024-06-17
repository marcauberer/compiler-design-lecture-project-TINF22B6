package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ASTAfterIfNode extends ASTNode {

  private boolean isElseIf = false;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitAfterIf(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTElsePreNode.getSelectionSet();
  }

  public ASTElsePostNode getElsePost() {
    return getChild(ASTElsePostNode.class, 0);
  }
}
