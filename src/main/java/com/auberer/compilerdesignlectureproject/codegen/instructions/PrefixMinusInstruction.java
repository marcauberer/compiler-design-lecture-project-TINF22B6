package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTAtomicExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class PrefixMinusInstruction extends Instruction {

    private final ASTAtomicExprNode rightOperand;

    public PrefixMinusInstruction(ASTNode node, ASTAtomicExprNode rightOperand) {
        super(node);
        this.rightOperand = rightOperand;
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": binary operation: prefix_expr");

    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("minus ");

        sb.append(rightOperand.getValue().getName());
    }

    @Override
    public void run(InterpreterEnvironment env) {

    }
}
