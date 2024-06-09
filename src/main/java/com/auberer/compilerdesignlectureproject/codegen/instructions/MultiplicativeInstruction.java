package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.ast.ASTPrefixExprNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class MultiplicativeInstruction extends Instruction {

    private final ASTPrefixExprNode leftOperand;
    private final ASTPrefixExprNode rightOperand;

    public MultiplicativeInstruction(ASTNode node, ASTPrefixExprNode leftOperand, ASTPrefixExprNode rightOperand) {
        super(node);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": binary operation: multiplicative_expr");

    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("mul ");

        sb.append(leftOperand.getValue().getName());
        sb.append(", ");
        sb.append(rightOperand.getValue().getName());
    }

    @Override
    public void run(InterpreterEnvironment env) {

    }
}
