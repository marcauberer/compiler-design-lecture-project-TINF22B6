package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTWhileLoopNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitWhileLoop(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_WHILE);
  }
  
  public ASTLogicalExprNode getCondition() {
    return getChild(ASTLogicalExprNode.class, 0);
  }

  public ASTStmtLstNode getBody(){
    return getChild(ASTStmtLstNode.class, 0);
  }
}
