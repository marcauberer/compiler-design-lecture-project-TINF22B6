package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTMultiplicativeExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import com.auberer.compilerdesignlectureproject.sema.SuperType;

public class PlusInstruction extends Instruction {

    private final ASTMultiplicativeExprNode leftOperand;
    private final ASTMultiplicativeExprNode rightOperand;

    public PlusInstruction(ASTNode node, ASTMultiplicativeExprNode leftOperand, ASTMultiplicativeExprNode rightOperand) {
        super(node);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": binary operation: additive_expr");

    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("plus ");

        sb.append(leftOperand.getValue().getName());
        sb.append(", ");
        sb.append(rightOperand.getValue().getName());
    }

    @Override
    public void run(InterpreterEnvironment env) {
        Value value = new Value(node);

        if (node.getType().is(SuperType.TY_DOUBLE)){
            double doubleValue = leftOperand.getValue().getDoubleValue();
            double doubleValue2 = rightOperand.getValue().getDoubleValue();
            value.setDoubleValue(doubleValue + doubleValue2);

        } else if (node.getType().is(SuperType.TY_INT)) {
            int intValue = leftOperand.getValue().getIntValue();
            int intValue2 = rightOperand.getValue().getIntValue();
            value.setIntValue(intValue + intValue2);
        }

        node.setValue(value);
    }
}
