package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ASTElsePostNode extends ASTNode {

  public enum ElseType {
    ELSE_IF,
    ELSE
  }

  ElseType exprType;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitElsePost(this);
  }

  public static Set<TokenType> getSelectionSet() {
    Set<TokenType> selectionSet = new HashSet<>();
    selectionSet.addAll(ASTIfStmtNode.getSelectionSet());
    selectionSet.addAll(ASTElseNode.getSelectionSet());
    return selectionSet;
  }

  public ASTIfStmtNode getIfStmt() {
    return getChild(ASTIfStmtNode.class, 0);
  }
}
