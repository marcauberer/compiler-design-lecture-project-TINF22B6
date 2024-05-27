package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class Scope {

  SymbolTable symbolTable = new SymbolTable(this);
  Scope parent = null; // Holds the parent scope of this one. The root scope has a null parent.
  List<Scope> children = new ArrayList<>();

  public void createChildScope() {
    Scope childScope = new Scope();
    childScope.parent = this;
    children.add(childScope);
  }

  public void insertSymbol(String name, ASTNode declNode) {
    symbolTable.insert(name, declNode);
  }

  public SymbolTableEntry lookupSymbol(String name, ASTNode lookupNode) {
    return symbolTable.lookup(name, lookupNode);
  }

  public SymbolTableEntry lookupSymbolStrict(String name, ASTNode lookupNode) {
    return symbolTable.lookupStrict(name, lookupNode);
  }
}
