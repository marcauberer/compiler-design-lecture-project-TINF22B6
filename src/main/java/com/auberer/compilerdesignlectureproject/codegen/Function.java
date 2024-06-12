package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.sema.Type;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Function implements IDumpable {

  private final String name;
  private BasicBlock entryBlock;
  private final List<Parameter> parameters;

  public Function(String name, List<Parameter> parameters) {
    this.name = name;
    this.parameters = parameters;
  }

  public void setEntryBlock(BasicBlock entryBlock) {
    assert this.entryBlock == null;
    this.entryBlock = entryBlock;
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("function ").append(name).
            append("(");
    String params = parameters.stream().map(parameter -> parameter.getType() + " " + parameter.getName()).collect(Collectors.joining(","));

    sb.append(params);
    sb.append(")");
    sb.append(": {\n");
    List<BasicBlock> dumpedBlocks = new ArrayList<>();
    entryBlock.dumpIR(sb, dumpedBlocks);
    sb.append("}\n\n");
  }

  @Getter
  public static class Parameter{
    private final String name;
    private final Type type;

    public Parameter(String name, Type type) {
      this.name = name;
      this.type = type;
    }
  }
}
