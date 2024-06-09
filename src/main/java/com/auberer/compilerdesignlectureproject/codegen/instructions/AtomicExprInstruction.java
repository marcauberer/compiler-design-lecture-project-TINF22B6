package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class AtomicExprInstruction extends Instruction {
    public AtomicExprInstruction(ASTNode node) {
        super(node);
    }

    @Override
    public void trace(StringBuilder sb) {

    }

    @Override
    public void dumpIR(StringBuilder sb) {

    }

    @Override
    public void run(InterpreterEnvironment env) {

    }
}
