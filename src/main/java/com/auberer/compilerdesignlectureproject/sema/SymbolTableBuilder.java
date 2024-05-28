package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import lombok.Getter;

import java.util.Stack;

@Getter
public class SymbolTableBuilder extends ASTVisitor<Void> {

  Stack<Scope> currentScopes = new Stack<>();

  public SymbolTableBuilder() {
    assert currentScopes.empty();
    currentScopes.push(new Scope());
  }

  @Override
  public Void visitEntry(ASTEntryNode node) {
    visitChildren(node);

    // Check if main function is present
    //if (currentScopes.peek().lookupSymbol("main", node) == null)
    //  throw new SemaError(node, "No main function found");

    return null;
  }

  @Override
  public Void visitVarDecl(ASTVarDeclNode node) {
    visitChildren(node);

    String variableName = node.getVariableName();
    SymbolTableEntry entry = currentScopes.peek().lookupSymbolStrict(variableName, node);
    if (entry == null) {
      currentScopes.peek().insertSymbol(variableName, node);
    } else {
      throw new SemaError(node, "The variable '" + variableName + "' has already been declared in this scope");
    }

    return null;
  }

  public Void visitAssignStmt(ASTAssignStmtNode node) {
    visitChildren(node);

    if (node.isAssignment()) {
      String variableName = node.getVariableName();
      SymbolTableEntry entry = currentScopes.peek().lookupSymbol(variableName, node);
      if (entry == null)
        throw new SemaError(node, "Variable '" + variableName + "' was not found");
    }
    
    return null;
  }

  @Override
  public Void visitWhileLoop(ASTWhileLoopNode node){
    Scope whileLoopScope = new Scope();
    currentScopes.push(whileLoopScope);
    
    visitChildren(node);
    
    currentScopes.pop();
    return null;
  }
  
  @Override
  public Void visitIf(ASTIfStmtNode node) {
    Scope ifScope = new Scope();
    currentScopes.push(ifScope);

    visitChildren(node);

    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitElse(ASTElseNode node) {
    Scope elseScope = new Scope();
    currentScopes.push(elseScope);

    visitChildren(node);

    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitDoWhileLoop(ASTDoWhileLoopNode node) {
    Scope doWhileScope = new Scope();
    currentScopes.push(doWhileScope);

    visitChildren(node);

    assert currentScopes.peek() == doWhileScope;
    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitForLoop(ASTForNode node) {
    Scope forScope = new Scope();
    currentScopes.push(forScope);

    visitChildren(node);

    // Check if the initialization, condition, and increment are not null
    assert node.getInitialization() != null;
    assert node.getCondition() != null;
    assert node.getIncrement() != null;

    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitSwitchStmt(ASTSwitchStmtNode node) {
    Scope scope = new Scope();
    currentScopes.push(scope);

    visitChildren(node);

    assert currentScopes.peek() == scope;
    currentScopes.pop();
    return null;
  }

  @Override
  public Void visitCases(ASTCasesNode node) {
    for (ASTStmtLstNode stmtLst : node.getStmtLists()) {
      Scope scope = new Scope();
      currentScopes.push(scope);

      visit(stmtLst);

      assert currentScopes.peek() == scope;
      currentScopes.pop();
    }

    return null;
  }

  @Override
  public Void visitDefault(ASTDefaultNode node) {
    Scope scope = new Scope();
    currentScopes.push(scope);

    visitChildren(node);

    assert currentScopes.peek() == scope;
    currentScopes.pop();
    return null;
  }
}
