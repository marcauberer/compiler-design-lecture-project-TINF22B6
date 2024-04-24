package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
public abstract class ASTNode implements IVisitable {

  public void addChild(ASTNode child) {
    children.add(child);
    child.setParent(this);
  }

  public <T> ArrayList<T> getChildren(Class<T> targetClass) {
    return children.stream()
        .filter(targetClass::isInstance)
        .map(targetClass::cast)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public <T> T getChild(Class<T> targetClass, int idx) {
    return getChildren(targetClass).get(idx);
  }

  ASTNode parent;
  ArrayList<ASTNode> children;
  CodeLoc codeLoc;
  // SymbolType symbolType = SymbolType(TY_INVALID);
}
