package com.auberer.compilerdesignlectureproject.sema;

import java.util.Stack;

import com.auberer.compilerdesignlectureproject.ast.ASTAfterIfNode;
import com.auberer.compilerdesignlectureproject.ast.ASTElseNode;
import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTPrintBuiltinCallNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;

/**
 * Typ-Kompatibilität prüfen
 * Overload resolution (Gruppe 6)
 * AST-Knoten mit den Typen befüllen (setEvaluatedSymbolType)
 * SymbolTableEntry mit Typ ergänzen
 * Sinnvolle Fehlermeldungen erzeugen
 */
public class TypeChecker extends ASTVisitor<ExprResult> {

  Stack<Scope> currentScopes = new Stack<>();

  public TypeChecker() {
    assert currentScopes.empty();
  }

  @Override
  public ExprResult visitEntry(ASTEntryNode node) {
    return super.visitEntry(node);
  }

  @Override
  public ExprResult visitPrintBuiltin(ASTPrintBuiltinCallNode node) {
    ASTLogicalExprNode logicalExprNode = node.logicalExpr();
    ExprResult logicalExprResult = visit(logicalExprNode);
    if (!logicalExprNode.getType().isOneOf(SuperType.TY_INT, SuperType.TY_DOUBLE, SuperType.TY_STRING))
      throw new SemaError(node, "Print statement expects int, double or string, but got '" + logicalExprResult.getType().toString() + "'");

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitLogicalExpr(ASTLogicalExprNode node) {
    visitChildren(node);
    return new ExprResult(new Type(SuperType.TY_BOOL));
  }

  @Override
  public ExprResult visitIf(ASTIfStmtNode node) {
    ASTLogicalExprNode logicalExprNode = node.getCondition();
    ExprResult logicalExprResult = visit(logicalExprNode);
    if (!logicalExprResult.getType().is(SuperType.TY_BOOL)) {
      throw new SemaError(node, String.format("if statement expects bool but got'%s", logicalExprResult.getType().toString()));
    }
    return new ExprResult(new Type(SuperType.TY_EMPTY));
  }

  @Override
  public ExprResult visitAfterIf(ASTAfterIfNode node) {
    return new ExprResult(new Type(SuperType.TY_EMPTY));
  }

  @Override
  public ExprResult visitElse(ASTElseNode node) {
    return new ExprResult(new Type(SuperType.TY_EMPTY));
  }
}
