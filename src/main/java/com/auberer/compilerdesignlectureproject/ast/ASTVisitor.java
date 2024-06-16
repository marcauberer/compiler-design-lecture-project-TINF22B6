package com.auberer.compilerdesignlectureproject.ast;

public class ASTVisitor<T> {

  // Generic visit methods

  public T visit(ASTNode node) {
    return node.accept(this);
  }

  public T visitChildren(ASTNode node) {
    for (ASTNode child : node.getChildren())
      child.accept(this);
    return null;
  }

  // Node-specific visit methods

  public T visitEntry(ASTEntryNode node) {
    return visitChildren(node);
  }

  public T visitStmtLst(ASTStmtLstNode node) {
    return visitChildren(node);
  }

  public T visitStmt(ASTStmtNode node) {
    return visitChildren(node);
  }

  public T visitPrintBuiltin(ASTPrintBuiltinCallNode node) {
    return visitChildren(node);
  }

  public T visitType(ASTTypeNode node) {
    return visitChildren(node);
  }

  public T visitFctDef(ASTFctDefNode node) {
    return visitChildren(node);
  }

  public T visitParamLst(ASTParamLstNode node) {
    return visitChildren(node);
  }

  public T visitParam(ASTParamNode node) {
    return visitChildren(node);
  }

  public T visitLogic(ASTLogicNode node) {
    return visitChildren(node);
  }

  public T visitFctCall(ASTFctCallNode node) {
    return visitChildren(node);
  }

  public T visitCallParams(ASTCallParamsNode node) {
    return visitChildren(node);
  }

  public T visitVarDecl(ASTVarDeclNode node) {
    return visitChildren(node);
  }

  public T visitAssignStmt(ASTAssignStmtNode node) {
    return visitChildren(node);
  }

  public T visitDoWhileLoop(ASTDoWhileLoopNode node) {
    return visitChildren(node);
  }

  public T visitWhileLoop(ASTWhileLoopNode node) {
    return visitChildren(node);
  }

  public T visitForLoop(ASTForNode node) {
    return visitChildren(node);
  }

  public T visitSwitchStmt(ASTSwitchStmtNode node) {
    return visitChildren(node);
  }

  public T visitCases(ASTCasesNode node) {
    return visitChildren(node);
  }

  public T visitCase(ASTCaseNode node) {
    return visitChildren(node);
  }

  public T visitDefault(ASTDefaultNode node) {
    return visitChildren(node);
  }

  public T visitLogicalExpr(ASTLogicalExprNode node) {
    return visitChildren(node);
  }

  public T visitCompareExpr(ASTCompareExprNode node) {
    return visitChildren(node);
  }

  public T visitAdditiveExpr(ASTAdditiveExprNode node) {
    return visitChildren(node);
  }

  public T visitMultiplicativeExpr(ASTMultiplicativeExprNode node) {
    return visitChildren(node);
  }

  public T visitPrefixExpr(ASTPrefixExprNode node) {
    return visitChildren(node);
  }

  public T visitAtomicExpr(ASTAtomicExprNode node) {
    return visitChildren(node);
  }

  public T visitIf(ASTIfStmtNode node) {
    return visitChildren(node);
  }

  public T visitAfterIf(ASTAfterIfNode node) {
    return visitChildren(node);
  }

  public T visitElsePre(ASTElsePreNode node) {
    return visitChildren(node);
  }

  public T visitElsePost(ASTElsePostNode node) {
    return visitChildren(node);
  }

  public T visitElse(ASTElseNode node) {
    return visitChildren(node);
  }

}