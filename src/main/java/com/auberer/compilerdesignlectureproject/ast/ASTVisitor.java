package com.auberer.compilerdesignlectureproject.ast;

public class ASTVisitor<T> {

  // Generic visit methods

  T visit(ASTNode node) {
    return node.accept(this);
  }

  T visitChildren(ASTNode node) {
    for (ASTNode child : node.getChildren())
      child.accept(this);
    return null;
  }

  // Node-specific visit methods

  T visitEntry(ASTEntryNode node) {
    return visitChildren(node);
  }

  T visitStmtLst(ASTStmtLstNode node) {
    return visitChildren(node);
  }

  T visitStmt(ASTStmtNode node) {
    return visitChildren(node);
  }

  T visitPrintBuiltin(ASTPrintBuiltinCallNode node) {
    return visitChildren(node);
  }

  T visitType(ASTTypeNode node) {
    return visitChildren(node);
  }
  
  T visitDoWhile(ASTDoWhileLoopNode node) {
    return visitChildren(node);
  }

  T visitWhileLoop(ASTWhileLoopNode node) {
    return visitChildren(node);
  }

  T visitSwitchStmt(ASTSwitchStmtNode node) {
    return visitChildren(node);
  }

  T visitCases(ASTCasesNode node) {
    return visitChildren(node);
  }

  T visitDefault(ASTDefaultNode node) {
    return visitChildren(node);
  }

  T visitLogicalExpr(ASTLogicalExpressionNode node) {
    return visitChildren(node);
  }

  T visitCompareExpr(ASTcompareExprNode node) {
    return visitChildren(node);
  }

  T visitAdditiveExpr(ASTAdditiveExprNode node) {
    return visitChildren(node);
  }

  T visitMultiplicativeExpr(ASTMultiplicativeExprNode node) {
    return visitChildren(node);
  }

  T visitPrefixExpr(ASTPrefixExprNode node) {
    return visitChildren(node);
  }

  T visitAtomicExpr(ASTAtomicExprNode node) {
    return visitChildren(node);
  }
  
  // ToDo: Add additional visit methods here

}
