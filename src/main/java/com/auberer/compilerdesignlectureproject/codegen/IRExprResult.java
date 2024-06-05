package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableEntry;
import lombok.Data;

@Data
public class IRExprResult {
  private Value value;
  private ASTNode node;
  private SymbolTableEntry entry;

  public IRExprResult(Value value) {
    this.value = value;
  }

  public IRExprResult(Value value, ASTNode node) {
    this.value = value;
    this.node = node;
  }

  public IRExprResult(Value value, ASTNode node, SymbolTableEntry entry) {
    this.value = value;
    this.node = node;
    this.entry = entry;
  }
}
