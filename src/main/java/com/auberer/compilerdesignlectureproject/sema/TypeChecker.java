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

  public TypeChecker(){
    this(null);
  }

  @Override
  public ExprResult visitEntry(ASTEntryNode node)
  {
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
  public ExprResult visitWhileLoop(ASTWhileLoopNode node) {
    ASTLogicalExprNode logicalExprNode = node.getLogicalExpr();
    ExprResult logicalExprResult = visit(logicalExprNode);

    // if (!logicalExprNode.getType().isOneOf(SuperType.TY_BOOL))
    if (!logicalExprResult.getType().isOneOf(SuperType.TY_BOOL))
      throw new SemaError(node, "While Loop - Condition expects boolean but got '" + logicalExprResult.getType().toString() + "'");

    Type resultType = new Type(SuperType.TY_EMPTY);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitLogicalExpr(ASTLogicalExprNode node) {
    visitChildren(node);
    return new ExprResult(new Type(SuperType.TY_BOOL));
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
  public ExprResult visitFctDef(ASTFctDefNode node) {

    FunctionDef def = new FunctionDef(node);
    if(! (entryNode.getFunctionDefOrNull(def) == null) ){
      throw new SemaError(node, "function definition for function " + entryNode.getFunctionDefOrNull(def)  + " is already defined");
    };

    entryNode.defineFunction(def);

    ASTTypeNode typeNode = node.getDataType();
    ExprResult type = visitType(typeNode);

    if (!type.getType().isOneOf(SuperType.TY_BOOL, SuperType.TY_INT, SuperType.TY_STRING, SuperType.TY_DOUBLE, SuperType.TY_EMPTY))
      throw new SemaError(node, "function definition expects type of boolean string, int , empty or double, but got '" +
              type.getType().toString() + "'");


    if(node.hasParams()){
      ASTParamLstNode params = node.getParams();
      for(ASTParamNode paramNode: params.getParamNodes()){
        visitParam(paramNode);
      }
    }

    node.setEvaluatedSymbolType(type.getType());

    Type resultType = new Type(SuperType.TY_FUNCTION);
    return new ExprResult(node.setEvaluatedSymbolType(resultType));
  }

  @Override
  public ExprResult visitFctCall(ASTFctCallNode node) {

    FunctionDef def = new FunctionDef(node);
    if(entryNode.getFunctionDefOrNull(def) == null){
      throw new SemaError(node, "function " + def + "is not yet defined");
    }
    ASTCallParamsNode params = node.getCallParams();

    for (ASTLogicalExprNode exprNode: params.getParamsAsLogicNodes()){
      ExprResult result = visit(exprNode);
      if (!result.getType().isOneOf(SuperType.TY_BOOL, SuperType.TY_INT, SuperType.TY_STRING, SuperType.TY_DOUBLE))
        throw new SemaError(node, "fct call statement expects type of boolean string, int or double, but got '" +
                result.getType().toString() + "'");

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
}
