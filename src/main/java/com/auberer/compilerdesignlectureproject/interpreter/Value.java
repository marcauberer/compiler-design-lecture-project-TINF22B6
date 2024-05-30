package com.auberer.compilerdesignlectureproject.interpreter;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.sema.SuperType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Value {

  // Counter will be incremented for each new unnamed value
  private static int counter = 0;

  private final ASTNode node;

  private final String name;

  // Which of the following is filled, depends on the type of the value
  @Setter
  private int intValue = 0;

  @Setter
  private double doubleValue = 0.0;

  @Setter
  private String stringValue = "";

  @Setter
  private boolean boolValue = false;

  public Value(ASTNode node) {
    this.node = node;
    this.name = "v" + counter++;
  }

  public Value(ASTNode node, String name) {
    this.node = node;
    this.name = name;
  }

  public boolean isTrue() {
    return boolValue;
  }

  public boolean isFalse() {
    return !boolValue;
  }

  @Override
  public String toString() {
    SuperType superType = node.getType().getSuperType();
    return switch (superType) {
      case TY_INT -> Integer.toString(intValue);
      case TY_DOUBLE -> Double.toString(doubleValue);
      case TY_STRING -> stringValue;
      case TY_BOOL -> Boolean.toString(boolValue);
      default -> throw new RuntimeException("Type not supported for printing: " + superType);
    };
  }
}
