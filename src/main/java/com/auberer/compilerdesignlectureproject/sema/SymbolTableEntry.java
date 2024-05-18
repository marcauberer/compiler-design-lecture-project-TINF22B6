package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;

import java.io.Serializable;

public class SymbolTableEntry implements Serializable {

  String name;
  ASTNode declNode;
  // Type type = new Type(TY_INVALID);

  public SymbolTableEntry(String name, ASTNode declNode) {
    this.name = name;
    this.declNode = declNode;
  }

}
