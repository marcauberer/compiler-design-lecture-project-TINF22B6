package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.sema.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ASTCasesNode extends ASTNode {

  public enum CaseType{
    INT_LIT,
    DOUBLE_LIT,
    STRING_LIT
  }

  private int casesSize = 0;
  private CaseType expectedType;
  private List<CaseType> caseTypes = new ArrayList<>();

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

  public CaseType getExpectedType() {
    return expectedType;
  }

  public void setExpectedType(CaseType type) {
    this.expectedType = type;
  }

  public List<CaseType> getCaseTypes() {
    return caseTypes;
  }

  public void addCaseType(CaseType type){
    caseTypes.add(type);
  }
}
