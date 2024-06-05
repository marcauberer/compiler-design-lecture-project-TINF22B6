package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.codegen.instructions.CondJumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.JumpInstruction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BasicBlock implements IDumpable {

  private final String label;
  private final List<Instruction> instructions = new ArrayList<>();

  public BasicBlock(String label) {
    this.label = label;
  }

  public void pushInstruction(Instruction instruction) {
    instructions.add(instruction);
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append(label).append(":\n");
    for (Instruction instruction : instructions) {
      sb.append("  ");
      instruction.dumpIR(sb);
      sb.append("\n");
    }
  }

  /**
   * Only dump the IR of this block if it has not been dumped yet.
   *
   * @param sb                  StringBuilder to append the IR to
   * @param alreadyDumpedBlocks List of blocks that have already been dumped
   */
  public void dumpIR(StringBuilder sb, List<BasicBlock> alreadyDumpedBlocks) {
    // Check if this block has already been dumped
    if (alreadyDumpedBlocks.contains(this) || instructions.isEmpty())
      return;
    alreadyDumpedBlocks.add(this);

    // Dump the IR of this block
    dumpIR(sb);

    // Dump the IR of the successor blocks
    Instruction lastInstruction = instructions.getLast();
    if (lastInstruction instanceof CondJumpInstruction condJumpInstruction) {
      condJumpInstruction.getTrueTargetBlock().dumpIR(sb, alreadyDumpedBlocks);
      condJumpInstruction.getFalseTargetBlock().dumpIR(sb, alreadyDumpedBlocks);
    } else if (lastInstruction instanceof JumpInstruction jumpInstruction) {
      jumpInstruction.getTargetBlock().dumpIR(sb, alreadyDumpedBlocks);
    }
  }
}
