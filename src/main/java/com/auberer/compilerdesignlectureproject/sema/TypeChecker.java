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
  public ExprResult visitForLoop(ASTForNode node) {
    ASTLogicalExprNode logicalNode = node.getCondition();
    ExprResult forNodeResult = visit(logicalNode);

    ExprResult initResult = visit(node.getInitialization());
    //assert initResult.getType().equals(new Type(SuperType.TY_INVALID));

    if (!forNodeResult.getType().is(SuperType.TY_BOOL))
      throw new SemaError(node, "Boolean Statement expected, but instead got '" + forNodeResult.getType().toString() + "'");

    ExprResult incrementResult = visit(node.getIncrement());
    //assert incrementResult.getType().equals(new Type(SuperType.TY_INVALID));

    ExprResult bodyResult = visit(node.getBody());
    //assert bodyResult.getType().equals(new Type(SuperType.TY_INVALID));

    Type resultType = new Type(SuperType.TY_INVALID);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
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
    ExprResult left = visit(node.operands().get(0));
    for (int i = 1; i < node.operands().size(); i++){
      ExprResult right = visit(node.operands().get(i));
      if (!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in logical expression");
      }
    }
    Type resultType = left.getType();
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitType(ASTTypeNode node) {
    Type resultType = switch (node.getDataType()) {
      case INT -> new Type(SuperType.TY_INT);
      case DOUBLE -> new Type(SuperType.TY_DOUBLE);
      case STRING -> new Type(SuperType.TY_STRING);
      case BOOL -> new Type(SuperType.TY_BOOL);
      case EMPTY -> new Type(SuperType.TY_EMPTY);
      default -> new Type(SuperType.TY_INVALID);
    };
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitAdditiveExpr(ASTAdditiveExprNode node) {
    ExprResult left = visit(node.operands().get(0));
    for (int i = 1; i < node.operands().size(); i++){
      ExprResult right = visit(node.operands().get(i));
      if(!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in additive expression");
      }
    }
    Type resultType = left.getType();
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitWhileLoop(ASTWhileLoopNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult logicalExprResult = visit(logicalExprNode);

    if (!logicalExprResult.getType().isOneOf(SuperType.TY_BOOL))
      throw new SemaError(node, "While Loop - Condition expects boolean but got '" + logicalExprResult.getType().toString() + "'");

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitCompareExpr(ASTCompareExprNode node) {
    ExprResult left = visit(node.operands().get(0));
    if(node.operands().size() > 1){
      ExprResult right = visit(node.operands().get(1));

      if(!left.getType().equals(right.getType())){
        throw new RuntimeException("Type mismatch in compare expression");
      }
      return new ExprResult(node.setEvaluatedSymbolType(new Type(SuperType.TY_BOOL)));
    }
    return new ExprResult(node.setEvaluatedSymbolType(left.getType()));
  }

  @Override
  public ExprResult visitAtomicExpr(ASTAtomicExprNode node) {
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
  public ExprResult visitMultiplicativeExpr(ASTMultiplicativeExprNode node) {
    ExprResult left = visit(node.operands().get(0));
    for (int i = 1; i < node.operands().size(); i++){
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

  @Override
  public ExprResult visitIf(ASTIfStmtNode node) {
    ASTLogicalExprNode logicalExprNode = node.getCondition();
    
    ExprResult logicalExprResult = visit(logicalExprNode);
    if (!logicalExprResult.getType().is(SuperType.TY_BOOL))
      throw new SemaError(node, String.format("if statement expects bool but got'%s", logicalExprResult.getType().toString()));
    
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
  
  @Override
  public ExprResult visitDoWhileLoop(ASTDoWhileLoopNode node) {
    ASTLogicalExprNode logicalExprNode = node.getCondition();
    ExprResult logicalExprResult = visit(logicalExprNode);

    if (!logicalExprResult.getType().is(SuperType.TY_BOOL))
      throw new SemaError(node, "While statement expects bool, but got '" + logicalExprResult.getType().toString() + "'");
    
    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitSwitchStmt(ASTSwitchStmtNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult result = visitLogicalExpr(logicalExprNode);
    if(!result.getType().isOneOf(SuperType.TY_INT, SuperType.TY_DOUBLE, SuperType.TY_STRING)){
      throw new SemaError(node, "Switch statement expects int, double or string, but got '" + result.getType().toString() + "'");
    }

    if(result.getType().getSuperType().equals(SuperType.TY_DOUBLE)){
      node.getCases().setExpectedType(ASTCasesNode.CaseType.DOUBLE_LIT);
    }
    else if(result.getType().getSuperType().equals(SuperType.TY_INT)){
      node.getCases().setExpectedType(ASTCasesNode.CaseType.INT_LIT);
    }
    else if(result.getType().getSuperType().equals(SuperType.TY_STRING)){
      node.getCases().setExpectedType(ASTCasesNode.CaseType.STRING_LIT);
    }


    visit(node.getCases());
    visit(node.getDefault());

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitVarDecl(ASTVarDeclNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult logicalExprResult = visit(logicalExprNode);
    ASTTypeNode typeNode = node.getDataType();
    Type declaredType = visit(typeNode).getType();

    if (!logicalExprResult.getType().is(declaredType.getSuperType())) {
      throw new SemaError(node, "Variable Declaration - Type mismatch: cannot assign type '"
              + logicalExprResult.getType().toString() + "' to variable of type '" + declaredType.toString() + "'");
    }

    Type resultType = new Type(SuperType.TY_INVALID);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitCases(ASTCasesNode node) {
    for(ASTCasesNode.CaseType t: node.getCaseTypes()){
      if(t != node.getExpectedType()){
        throw new SemaError(node, "Switch case expects '" + node.getExpectedType() + "' but got '" + t + "'");
      }
    }
    return super.visitCases(node);
  }

  @Override
  public ExprResult visitDefault(ASTDefaultNode node) {
    visitChildren(node);

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitAssignStmt(ASTAssignStmtNode node) {
    SymbolTableEntry currentSymbol = node.getCurrentSymbol();
    if (currentSymbol != null) {
      Type leftType = currentSymbol.getType();
      ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
      ExprResult logicalExprResult = visit(logicalExprNode);

      if (!logicalExprResult.getType().is(leftType.getSuperType()))
        throw new SemaError(node, "AssignStmt expects'" + leftType.getSuperType() + ",' but got '" + logicalExprResult.getType().toString() + "'");
    }

    Type resultType = new Type(SuperType.TY_INVALID);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

}
