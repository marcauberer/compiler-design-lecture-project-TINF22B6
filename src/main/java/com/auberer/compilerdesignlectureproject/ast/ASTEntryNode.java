package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.sema.SuperType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ASTEntryNode extends ASTNode {
  public List<FunctionDef> definedFunctions = new ArrayList<>();

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitEntry(this);
  }

  public void defineFunction(FunctionDef functionDef) {
    definedFunctions.add(functionDef);
  }

  public FunctionDef getFunctionDefOrNull(FunctionDef def) {
    for (FunctionDef f : definedFunctions) {
      if (f.equals(def)) {
      return f;
      }
    }
    return null;
  }
}
