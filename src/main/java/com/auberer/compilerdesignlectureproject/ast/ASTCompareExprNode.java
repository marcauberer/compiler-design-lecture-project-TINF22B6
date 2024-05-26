package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
public class ASTCompareExprNode extends ASTNode {

  public enum CompareOperator {
    EQUAL,
    NOT_EQUAL,
  }

  public CompareOperator operator;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitCompareExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTPrefixExprNode.getSelectionSet();
  }

  public List<ASTAdditiveExprNode> operands() {
    return getChildren(ASTAdditiveExprNode.class);
  }
}
