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

  final ASTEntryNode entryNode;

  public TypeChecker(ASTEntryNode entryNode) {
    this.entryNode = entryNode;
    assert currentScopes.empty();
  }

  public TypeChecker() {
    this(null);
  }

  @Override
  public ExprResult visitForLoop(ASTForNode node) {
    ASTVarDeclNode initialization = node.getInitialization();
    ExprResult initResult = visit(initialization);
    assert initResult.getType().is(SuperType.TY_INVALID);

    ASTLogicalExprNode condition = node.getCondition();
    ExprResult forNodeResult = visit(condition);
    if (!forNodeResult.getType().is(SuperType.TY_BOOL))
      throw new SemaError(node, "Boolean Statement expected, but instead got '" + forNodeResult.getType().toString() + "'");

    ASTAssignStmtNode increment = node.getIncrement();
    ExprResult incrementResult = visit(increment);
    assert incrementResult.getType().is(SuperType.TY_INVALID);

    visit(node.getBody());

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
    ASTLogicalExprNode logicalExprNode = node.getCondition();
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
        SymbolTableEntry symbol = node.getCurrentSymbol();
        assert symbol != null;
        return new ExprResult(node.setEvaluatedSymbolType(symbol.getType()), symbol);
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
  public ExprResult visitVarDecl(ASTVarDeclNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult logicalExprResult = visit(logicalExprNode);
    ASTTypeNode typeNode = node.getDataType();
    Type declaredType = visit(typeNode).getType();

    if (!logicalExprResult.getType().is(declaredType.getSuperType())) {
      throw new SemaError(node, "Variable Declaration - Type mismatch: cannot assign type '"
              + logicalExprResult.getType().toString() + "' to variable of type '" + declaredType.toString() + "'");
    }
    SymbolTableEntry entry = node.getCurrentSymbol();
    entry.updateType(declaredType);

    Type resultType = new Type(SuperType.TY_INVALID);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitSwitchStmt(ASTSwitchStmtNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult result = visitLogicalExpr(logicalExprNode);
    if(!result.getType().isOneOf(SuperType.TY_INT, SuperType.TY_DOUBLE, SuperType.TY_STRING)){
      throw new SemaError(node, "Switch statement expects int, double or string, but got '" + result.getType().toString() + "'");
    }

    SuperType type = result.getType().getSuperType();
    ASTCaseNode.CaseType expectedType = ASTCaseNode.CaseType.INVALID;

    if(type.equals(SuperType.TY_DOUBLE)){
      expectedType = ASTCaseNode.CaseType.DOUBLE_LIT;
    }
    else if(type.equals(SuperType.TY_INT)){
      expectedType = ASTCaseNode.CaseType.INT_LIT;
    }
    else if(type.equals(SuperType.TY_STRING)){
      expectedType = ASTCaseNode.CaseType.STRING_LIT;
    }

    for(ASTCaseNode c: node.getCases()){
      c.setExpectedType(expectedType);
      visit(c);
    }

    visit(node.getDefault());

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitCase(ASTCaseNode node){
    if(node.getCaseType() != node.getExpectedType()){
      throw new SemaError(node, "Switch case expects '" + node.getExpectedType() + "' but got '" + node.getCaseType() + "'");
    }

    visitChildren(node);

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitDefault(ASTDefaultNode node) {
    visitChildren(node);

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitFctDef(ASTFctDefNode node) {

    FunctionDef def = new FunctionDef(node);
    if(! (entryNode.getFunctionDefOrNull(def) == null) ){
      throw new SemaError(node, "function definition for function " + entryNode.getFunctionDefOrNull(def)  + " is already defined");
    };

    ASTTypeNode typeNode = node.getDataType();
    ExprResult type = visitType(typeNode);

    if (!type.getType().isOneOf(SuperType.TY_BOOL, SuperType.TY_INT, SuperType.TY_STRING, SuperType.TY_DOUBLE, SuperType.TY_EMPTY))
      throw new SemaError(node, "function definition expects type of boolean string, int , empty or double, but got '" +
              type.getType().toString() + "'");

    if (node.hasParams()) {
      ASTParamLstNode params = node.getParams();
      for(ASTParamNode paramNode: params.getParamNodes()){
        visitParam(paramNode);
      }
    }

    node.setEvaluatedSymbolType(type.getType());
    entryNode.defineFunction(new FunctionDef(node));
    Type resultType = new Type(SuperType.TY_FUNCTION);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitFctCall(ASTFctCallNode node) {

    visitChildren(node);

    ASTCallParamsNode params = node.getCallParams();

    for (ASTLogicalExprNode exprNode: params.getParamsAsLogicNodes()){
      ExprResult result = visit(exprNode);
      if (!result.getType().isOneOf(SuperType.TY_BOOL, SuperType.TY_INT, SuperType.TY_STRING, SuperType.TY_DOUBLE))
        throw new SemaError(node, "fct call statement expects type of boolean string, int or double, but got '" +
                result.getType().toString() + "'");
      exprNode.setEvaluatedSymbolType(result.getType());
    }

    FunctionDef def = new FunctionDef(node);
    if(entryNode.getFunctionDefOrNull(def) == null){
      throw new SemaError(node, "function " + def + "is not yet defined");
    }


    Type resultType = new Type(SuperType.TY_FUNCTION);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  public ExprResult visitParam(ASTParamNode node) {
    ASTTypeNode typeNode = node.getDataType();
    ExprResult type = visitType(typeNode);
    if (!type.getType().isOneOf(SuperType.TY_BOOL, SuperType.TY_INT, SuperType.TY_STRING, SuperType.TY_DOUBLE))
      throw new SemaError(node, "param expects type of boolean string, int or double, but got '" +
              type.getType().toString() + "'");

    Type resultType = new Type(type.getType().getSuperType());
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
        throw new SemaError(node, "AssignStmt expects '" + leftType.getSuperType() + ",' but got '" + logicalExprResult.getType().toString() + "'");
    }

    Type resultType = new Type(SuperType.TY_INVALID);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

}
