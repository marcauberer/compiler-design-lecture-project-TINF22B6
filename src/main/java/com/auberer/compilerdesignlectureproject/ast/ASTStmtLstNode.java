package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTStmtLstNode extends ASTNode {
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitStmtLst(this);
  }

  public static Set<TokenType> getSelectionSet() {
    // The first set contains epsilon, so we also need the follow set of stmtLst
    Set<TokenType> followSet = Set.of(TokenType.TOK_RBRACE, TokenType.TOK_DEFAULT, TokenType.TOK_RETURN);

    // The selection set of stmtLst contains the selection set of stmt and the follow set of stmtLst
    Set<TokenType> selectionSet = new HashSet<>();
    selectionSet.addAll(ASTStmtNode.getSelectionSet());
    // ToDo: Add selection set of ifStmt
    // ToDo: Add selection set of whileLoop
    selectionSet.addAll(ASTDoWhileLoopNode.getSelectionSet());
    selectionSet.addAll(ASTWhileLoopNode.getSelectionSet());
    // ToDo: Add selection set of forLoop
    // ToDo: Add selection set of switchStmt
    selectionSet.addAll(ASTSwitchStmtNode.getSelectionSet());
    selectionSet.addAll(followSet);
    return selectionSet;
  }
}
