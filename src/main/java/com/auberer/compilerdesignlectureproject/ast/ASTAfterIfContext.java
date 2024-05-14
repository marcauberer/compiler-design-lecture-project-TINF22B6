package com.auberer.compilerdesignlectureproject.ast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ASTAfterIfContext extends ASTAfterIfNode {
  public enum DataType {
    ELSE_IF, ELSE, UNKNOWN
  }

  private DataType type = DataType.UNKNOWN;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitAfterIf(this);
  }

  // IF_BEFORE,
  // ELSE_BEFORE
}
