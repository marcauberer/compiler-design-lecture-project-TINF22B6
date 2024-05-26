package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ASTAssignStmtNode extends ASTNode {

  String variableName;
  boolean isAssignment = false;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitAssignStmt(this);
  }

  public static Set<TokenType> getSelectionSet() {
    Set<TokenType> identifier = ASTLogicalExprNode.getSelectionSet();
    identifier.add(TokenType.TOK_IDENTIFIER);
    return identifier;
  }

  public ASTLogicalExprNode getLogicalExpr() {
    return getChild(ASTLogicalExprNode.class, 0);
  }
}
