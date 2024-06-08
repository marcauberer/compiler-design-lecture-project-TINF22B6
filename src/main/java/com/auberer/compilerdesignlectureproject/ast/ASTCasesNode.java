package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.sema.Type;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.Token;

import java.util.*;

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

  @Getter
  private List<String> cases;

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

  public void setCases(List<String> cases) {
    this.cases = cases;
  }

  public int findCaseIndex(String caseValue){
    Optional<String> optionalString = cases.stream().filter(token -> token.equals(caseValue)).findAny();
    if(optionalString.isPresent()){
      String caseString = optionalString.get();
      return cases.indexOf(caseString);
    }
    return -1;
  }
}
