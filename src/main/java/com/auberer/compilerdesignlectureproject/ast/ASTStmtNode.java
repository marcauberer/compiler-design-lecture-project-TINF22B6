package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTStmtNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitStmt(this);
  }

  public static Set<TokenType> getSelectionSet() {
    // ToDo: Add selection set of varDecl
    // ToDo: Add selection set of assignExpr
    return Set.of();
  }
}

