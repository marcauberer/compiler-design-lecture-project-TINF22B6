package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.codegen.BasicBlock;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.sema.SuperType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CondJumpInstruction extends Instruction {

  private final ASTNode condition;
  private final BasicBlock trueTargetBlock;
  private final BasicBlock falseTargetBlock;

  public CondJumpInstruction(ASTNode node, ASTNode condition, BasicBlock trueTargetBlock, BasicBlock falseTargetBlock) {
    super(node);
    this.condition = condition;
    this.trueTargetBlock = trueTargetBlock;
    this.falseTargetBlock = falseTargetBlock;
  }

  @Override
  public void run(InterpreterEnvironment env) {
    assert condition.getType().is(SuperType.TY_BOOL);
    if (condition.getValue().isTrue()) {
      env.setInstructionIterator(trueTargetBlock.getInstructions().listIterator());
    } else {
      env.setInstructionIterator(falseTargetBlock.getInstructions().listIterator());
    }
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("condjump ").append(condition.getValue().getName()).append(" ? ").append(trueTargetBlock.getLabel()).append(" : ").append(falseTargetBlock.getLabel());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append("cond jump");
  }
}
