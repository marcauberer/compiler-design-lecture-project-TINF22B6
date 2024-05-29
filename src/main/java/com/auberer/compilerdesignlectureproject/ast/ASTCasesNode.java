package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.sema.Type;

import java.util.List;
import java.util.Set;

public class ASTCasesNode extends ASTNode {

  private int casesSize = 0;
  private Type expectedType;
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

  public Type getExpectedType() {
    return expectedType;
  }

  public void setExpectedType(Type type) {
    this.expectedType = type;
  }
}
