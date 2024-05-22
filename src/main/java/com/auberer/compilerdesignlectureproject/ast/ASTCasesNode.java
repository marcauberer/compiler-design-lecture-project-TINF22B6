package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.List;
import java.util.Set;

public class ASTCasesNode extends ASTNode {

  private int casesSize = 0;
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitCases(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_CASE);
  }

  public void setCasesSize(int casesSize){
    this.casesSize = casesSize;
  }

  public int getCasesSize() {
    return casesSize;
  }

  public List<ASTStmtLstNode> getStmtLists(){
    return getChildren(ASTStmtLstNode.class);
  }
}
