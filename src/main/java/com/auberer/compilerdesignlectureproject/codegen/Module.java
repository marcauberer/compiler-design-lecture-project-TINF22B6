package com.auberer.compilerdesignlectureproject.codegen;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a module in the code generation phase.
 * This corresponds to a single input source file.
 */
public class Module implements IDumpable {

  @Getter
  private final String name;
  private final List<Function> functions = new ArrayList<>();

  public Module(String name) {
    this.name = name;
  }

  public void addFunction(Function function) {
    functions.add(function);
  }

  public Function getFunction(String name) {
    for (Function function : functions)
      if (function.getName().equals(name))
        return function;
    return null;
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("module ").append(name).append(":");
    for (Function function : functions) {
      sb.append("\n\n");
      function.dumpIR(sb);
    }
  }
}
