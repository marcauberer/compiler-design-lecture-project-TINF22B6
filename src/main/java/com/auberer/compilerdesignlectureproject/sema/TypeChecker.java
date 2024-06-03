package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;

import java.util.Stack;

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
  public ExprResult visitLogicalExpr(ASTLogicalExprNode node) { //Schleife
    //visitChildren(node);
    //return new ExprResult(new Type(SuperType.TY_BOOL));

    ExprResult left = visit(node.operands().get(0));
    for (int j = 1; j <= node.operands().size(); j++){
      ExprResult right = visit(node.operands().get(j));
      if (!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in logical expression");
      }
    }
    Type resultType = left.getType();
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitAdditiveExpr(ASTAdditiveExprNode node) { //Schleife
    ExprResult left = visit(node.operands().get(0));
    for (int i = 0; i <= node.operands().size(); i++){
      ExprResult right = visit(node.operands().get(i));
      if(!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in additive expression");
      }
    }
    Type resultType = left.getType();
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitCompareExpr(ASTCompareExprNode node) {
    Type resultType;
    ExprResult left = visit(node.operands().get(0));
    if(node.operands().size() > 1){
      ExprResult right = visit(node.operands().get(1));

      if(!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in compare expression");
      }
      resultType = new Type(SuperType.TY_BOOL);
    }
    else {
      resultType = left.getType();
    }

    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitAtomicExpr(ASTAtomicExprNode node) { //switch case -- identifier -> lookuptable type bekommen --> fctcall visit fctcall
    ASTAtomicExprNode.AtomicType exprType = node.getExprType();

    switch (exprType) {
      case ASTAtomicExprNode.AtomicType.INT_LIT: {
        return new ExprResult(node.setEvaluatedSymbolType(new Type(SuperType.TY_INT)));
      }
      case ASTAtomicExprNode.AtomicType.DOUBLE_LIT: {
        return new ExprResult(node.setEvaluatedSymbolType(new Type(SuperType.TY_DOUBLE)));
      }
      case ASTAtomicExprNode.AtomicType.STRING_LIT: {
        return new ExprResult(node.setEvaluatedSymbolType(new Type(SuperType.TY_STRING)));
      }
      case ASTAtomicExprNode.AtomicType.BOOL_LIT: {
        return new ExprResult(node.setEvaluatedSymbolType(new Type(SuperType.TY_BOOL)));
      }
      case ASTAtomicExprNode.AtomicType.LOGICAL_EXPR: {
        return visitLogicalExpr(node.getLogicalExpr());
      }
      case ASTAtomicExprNode.AtomicType.IDENTIFIER: {
        return new ExprResult(node.setEvaluatedSymbolType(node.getCurrentSymbolTable().getType()));
      }
      case ASTAtomicExprNode.AtomicType.FCT_CALL: {
        return visitFctCall(node.getFctCall());
      }
      case ASTAtomicExprNode.AtomicType.PRINT_BUILTIN_CALL: {
        return visitPrintBuiltin(node.getPrintCall());
      }
    }
      return null;
  }

  @Override
  public ExprResult visitMultiplicativeExpr(ASTMultiplicativeExprNode node) { //Schleife
    ExprResult left = visit(node.operands().get(0));
    for (int i = 0; i <= node.operands().size(); i++){
      ExprResult right = visit(node.operands().get(i));
      if(!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in multiplicative expression");
      }
    }
    Type resultType = left.getType();
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitPrefixExpr(ASTPrefixExprNode node) {
    ExprResult left = visit(node.operand());
    if(node.operator != null){
      if(left.getType().isOneOf(SuperType.TY_INT, SuperType.TY_DOUBLE)){
        Type resultType = left.getType();
        return new ExprResult(node.setEvaluatedSymbolType(resultType));
      }
      else {
        throw new RuntimeException("Type mismatch in prefix expression");
      }
    }
    return left;
  }

}
