package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;

public class Scope {

  SymbolTable symbolTable = new SymbolTable(this);
  Scope parent = null; // Holds the parent scope of this one. The root scope has a null parent.

  public void insertSymbol(String name, ASTNode declNode) {
    symbolTable.insert(name, declNode);
  }

  public SymbolTableEntry lookupSymbol(String name) {
    return symbolTable.lookup(name);
  }

  public SymbolTableEntry lookupSymbolStrict(String name) {
    return symbolTable.lookupStrict(name);
  }
}
