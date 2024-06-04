package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.sema.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class ASTCasesNode extends ASTNode {

  public enum CaseType{
    INT_LIT,
    DOUBLE_LIT,
    STRING_LIT
  }

  @Setter
  private int casesSize = 0;
  @Setter
  private CaseType expectedType;
  private final List<CaseType> caseTypes = new ArrayList<>();

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitCases(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_CASE);
  }

  public List<ASTStmtLstNode> getStmtLists(){
    return getChildren(ASTStmtLstNode.class);
  }

  public void addCaseType(CaseType type){
    caseTypes.add(type);
  }
}
