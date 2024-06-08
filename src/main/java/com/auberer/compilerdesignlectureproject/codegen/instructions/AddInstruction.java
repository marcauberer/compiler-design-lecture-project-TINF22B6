package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableEntry;

public class AddInstruction extends Instruction {

    private final SymbolTableEntry entry;
    private final Instruction instruction;


    public AddInstruction(ASTNode node, SymbolTableEntry entry, Instruction instruction) {
        super(node);
        this.entry = entry;
        this.instruction = instruction;
    }

    @Override
    public void run(InterpreterEnvironment env) {
        entry.setValue(instruction.node.getValue());
    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("alloca ").append(entry.getName());
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": alloca ");
    }

}
