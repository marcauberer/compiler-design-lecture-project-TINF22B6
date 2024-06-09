package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTAdditiveExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class EqualInstruction extends Instruction{

    private final ASTAdditiveExprNode leftOperand;
    private final ASTAdditiveExprNode rightOperand;

    public EqualInstruction(ASTNode node, ASTAdditiveExprNode leftOperand, ASTAdditiveExprNode rightOperand) {
        super(node);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": binary operation: compare_expr");
    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("equal ");

        sb.append(leftOperand.getValue().getName());
        sb.append(", ");
        sb.append(rightOperand.getValue().getName());
    }

    @Override
    public void run(InterpreterEnvironment env) {

    }
}
