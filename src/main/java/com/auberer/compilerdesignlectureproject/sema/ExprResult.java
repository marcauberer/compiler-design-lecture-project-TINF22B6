package com.auberer.compilerdesignlectureproject.sema;

import lombok.Data;

@Data
public class ExprResult {
  private Type type;
  private SymbolTableEntry entry = null;

  public ExprResult(Type type) {
    this.type = type;
  }

  public ExprResult(Type type, SymbolTableEntry entry) {
    this.type = type;
    this.entry = entry;
  }
}
