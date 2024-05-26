package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class SymbolTableEntry implements Serializable {

  @Getter
  private String name;
  @Getter
  private ASTNode declNode;
  @Getter
  private Scope scope;
  @Setter
  @Getter
  private boolean isUsed = false;
  @Setter
  @Getter
  private boolean isParameter = false;
  @Getter
  private Type type = new Type(SuperType.TY_INVALID);

  public SymbolTableEntry(Scope scope, String name, ASTNode declNode) {
    this.scope = scope;
    this.name = name;
    this.declNode = declNode;
  }

  public void updateType(Type type) {
    assert this.type.getSuperType() == SuperType.TY_INVALID;
    this.type = type;
  }
}
