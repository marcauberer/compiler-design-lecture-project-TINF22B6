package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ASTWhileLoopNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitWhileLoop(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_WHILE);
  }
  
  public List<ASTAssignExprNode> getAssignExpr() {
    return this.getChildren(ASTAssignExprNode.class);
  }

  public List<ASTStmtLstNode> getStmtLst() {
    return this.getChildren(ASTStmtLstNode.class);
  }

}
