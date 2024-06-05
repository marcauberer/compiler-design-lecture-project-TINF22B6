package com.auberer.compilerdesignlectureproject.interpreter;

import com.auberer.compilerdesignlectureproject.codegen.Function;
import com.auberer.compilerdesignlectureproject.codegen.Module;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import lombok.Setter;

import java.util.ListIterator;

public class InterpreterEnvironment {

  private final Module irModule;
  private final boolean doTracing;
  @Setter
  private ListIterator<Instruction> instructionIterator;

  public InterpreterEnvironment(Module irModule, boolean doTracing) {
    this.irModule = irModule;
    this.doTracing = doTracing;
  }

  public void interpret() {
    // Search for main function
    Function mainFunction = irModule.getFunction("main");
    if (mainFunction == null)
      throw new RuntimeException("No main function found in module " + irModule.getName());

    // Main loop
    instructionIterator = mainFunction.getEntryBlock().getInstructions().listIterator();
    while (instructionIterator.hasNext()) {
      Instruction instruction = instructionIterator.next();
      if (doTracing) {
        System.out.println(instruction);
      }
      instruction.run(this);
    }
  }

}
