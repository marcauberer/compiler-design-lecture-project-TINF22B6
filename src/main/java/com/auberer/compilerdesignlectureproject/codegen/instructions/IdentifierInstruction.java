package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTAtomicExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class IdentifierInstruction extends Instruction{

    ASTAtomicExprNode node;

    public IdentifierInstruction(ASTAtomicExprNode node) {
        super(node);
        this.node = node;
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": binary operation: atomic_expr");

    }

    @Override
    public void dumpIR(StringBuilder sb) {
        String identifier = node.getIdentifier();
        sb.append("identifier: ").append(identifier);
    }

    @Override
    public void run(InterpreterEnvironment env) {

    }
}
