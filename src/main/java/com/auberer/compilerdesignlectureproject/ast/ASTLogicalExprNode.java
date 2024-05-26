package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ASTLogicalExprNode extends ASTNode {

  public enum LogicalOperator {
    AND,
    OR
  }

  public List<LogicalOperator> operatorList = new ArrayList<>();

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitLogicalExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTCompareExprNode.getSelectionSet();
  }

  public void operatorsListAdd(LogicalOperator a) {
    operatorList.add(a);
  }

    public List<ASTCompareExprNode> operands() {
        return getChildren(ASTCompareExprNode.class);
    }

}
