package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable implements Serializable {

  Map<String, SymbolTableEntry> symbols = new HashMap<>(); // Mapping between name and symbol table entry
  Scope scope; // Scope, where this symbol table lives in

  public SymbolTable(Scope scope) {
    this.scope = scope;
  }

  public void insert(String name, ASTNode declNode) {
    symbols.put(name, new SymbolTableEntry(name, declNode));
  }

  public SymbolTableEntry lookup(String name) {
    // Check if the symbol exists in the current scope
    if (symbols.containsKey(name))
      return symbols.get(name);
    // Return null if the current scope is the root scope
    if (scope.parent == null)
      return null;
    // Continue searching in the symbol table of the parent scope
    return scope.parent.symbolTable.lookup(name);
  }

  public SymbolTableEntry lookupStrict(String name) {
    return symbols.get(name);
  }

}
