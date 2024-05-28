package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class SymbolTableEntry implements Serializable {

  private final String name;

  private final ASTNode declNode;

  private final Scope scope;

  @Setter
  private boolean isUsed = false;

  @Setter
  private boolean isParameter = false;

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
