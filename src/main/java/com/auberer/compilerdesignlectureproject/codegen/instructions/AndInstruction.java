package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class AndInstruction extends Instruction {

    private final ASTCompareExprNode leftOperand;
    private final ASTCompareExprNode rightOperand;

    public AndInstruction(ASTLogicalExprNode node, ASTCompareExprNode leftOperand, ASTCompareExprNode rightOperand) {
        super(node);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AndInstruction(ASTNode node, ASTCompareExprNode leftOperand, ASTCompareExprNode rightOperand) {
        super(node);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": binary operation: logical_expr");
    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("and ");

        sb.append(leftOperand.getValue().getName());
        sb.append(", ");
        sb.append(rightOperand.getValue().getName());
    }

    @Override
    public void run(InterpreterEnvironment env) {

    }
}
