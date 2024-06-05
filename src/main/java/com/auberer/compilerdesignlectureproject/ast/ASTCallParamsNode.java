package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.List;
import java.util.Set;

public class ASTCallParamsNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitCallParams(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return ASTAssignStmtNode.getSelectionSet();
  }

  public List<ASTLogicalExprNode> getParamsAsLogicNodes() {
    return getChildren(ASTLogicalExprNode.class);
  }
}
