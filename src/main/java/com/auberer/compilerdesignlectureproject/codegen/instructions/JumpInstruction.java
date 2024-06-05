package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.codegen.BasicBlock;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import lombok.Getter;

@Getter
public class JumpInstruction extends Instruction {
  private final BasicBlock targetBlock;

  public JumpInstruction(ASTNode node, BasicBlock targetBlock) {
    super(node);
    this.targetBlock = targetBlock;
  }

  @Override
  public void run(InterpreterEnvironment env) {
    env.setInstructionIterator(targetBlock.getInstructions().listIterator());
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("jump ").append(targetBlock.getLabel());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append(node.getCodeLoc().toString()).append(": jump ");
  }

  @Override
  public boolean isTerminator() {
    return true;
  }
}
