package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.interpreter.Value;

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
        boolean leftValue = leftOperand.getValue().isTrue();
        boolean rightValue = rightOperand.getValue().isTrue();

        Value value = new Value(node);
        value.setBoolValue(leftValue && rightValue);
        node.setValue(value);
    }
}
