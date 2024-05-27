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
    symbols.put(name, new SymbolTableEntry(scope, name, declNode));
  }

  public SymbolTableEntry lookup(String name, ASTNode lookupNode) {
    SymbolTableEntry entry = lookupStrict(name, lookupNode);
    if (entry != null)
      return entry;
    if (scope.parent == null)
      return null;
    return scope.parent.lookupSymbol(name, lookupNode);
  }

  public SymbolTableEntry lookupStrict(String name, ASTNode lookupNode) {
    SymbolTableEntry entry = symbols.get(name);
    return entry != null && entry.declNode.getCodeLoc().compareTo(lookupNode.getCodeLoc()) <= 0 ? entry : null;
  }

}
