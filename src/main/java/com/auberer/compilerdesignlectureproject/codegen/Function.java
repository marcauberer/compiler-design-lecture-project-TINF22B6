package com.auberer.compilerdesignlectureproject.codegen;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Function implements IDumpable {

  private final String name;
  private BasicBlock entryBlock;

  public Function(String name) {
    this.name = name;
  }

  public void setEntryBlock(BasicBlock entryBlock) {
    assert this.entryBlock == null;
    this.entryBlock = entryBlock;
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("function ").append(name).append(": {\n");
    List<BasicBlock> dumpedBlocks = new ArrayList<>();
    entryBlock.dumpIR(sb, dumpedBlocks);
    sb.append("}\n\n");
  }
}
