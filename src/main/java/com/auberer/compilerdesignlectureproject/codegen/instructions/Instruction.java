package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import lombok.Getter;

public abstract class Instruction implements IInstruction {

  protected final ASTNode node;

  public Instruction(ASTNode node) {
    this.node = node;
  }

  /**
   * Can be overridden by subclasses to return true if the instruction is a terminator instruction
   *
   * @return Terminator instruction
   */
  public boolean isTerminator() {
    return false;
  }

}
