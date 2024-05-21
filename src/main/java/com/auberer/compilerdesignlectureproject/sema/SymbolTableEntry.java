package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class SymbolTableEntry implements Serializable {

  String name;
  ASTNode declNode;
  Scope scope;
  boolean isUsed = false;
  boolean isParameter = false;
  // Type type = new Type(TY_INVALID);

  public SymbolTableEntry(Scope scope, String name, ASTNode declNode) {
    this.scope = scope;
    this.name = name;
    this.declNode = declNode;
  }

}
