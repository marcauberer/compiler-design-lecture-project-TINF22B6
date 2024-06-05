package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableEntry;

public class StoreInstruction extends Instruction {

  private final SymbolTableEntry entry;

  public StoreInstruction(ASTNode node, SymbolTableEntry entry) {
    super(node);
    this.entry = entry;
  }

  @Override
  public void run(InterpreterEnvironment env) {
    entry.setValue(node.getValue());
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("store ").append(entry.getName());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append(node.getCodeLoc().toString()).append(": store");
  }
}
