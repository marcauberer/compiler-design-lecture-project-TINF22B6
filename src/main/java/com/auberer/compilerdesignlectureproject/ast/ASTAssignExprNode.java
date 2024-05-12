package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ASTAssignExprNode extends ASTNode {

  @Getter
  @Setter
  String variableName;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitAssignExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    Set<TokenType> identifier = ASTLogicalExprNode.getSelectionSet();
    identifier.add(TokenType.TOK_IDENTIFIER);
    return identifier;
  }
}
