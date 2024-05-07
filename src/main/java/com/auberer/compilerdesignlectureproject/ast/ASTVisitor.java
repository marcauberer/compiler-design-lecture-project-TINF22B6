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

  T visitFctDef(ASTFctDef node) {
    return visitChildren(node);
  }

  T visitParamLst(ASTParamLst node) {return visitChildren(node);}

  T visitLogic(ASTLogic node) {return visitChildren(node);}

  T visitFctCall(ASTFctCall node) {return visitChildren(node);}

  T visitCallParams(ASTCallParams node) {return visitChildren(node);}

  T visitAssignExpr(ASTAssignExpr node) {return visitChildren(node);}

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

  // ToDo: Add additional visit methods here

}
