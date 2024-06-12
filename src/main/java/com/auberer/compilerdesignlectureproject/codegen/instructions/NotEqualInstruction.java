package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTAdditiveExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import com.auberer.compilerdesignlectureproject.sema.SuperType;

public class NotEqualInstruction extends Instruction{

    private final ASTAdditiveExprNode leftOperand;
    private final ASTAdditiveExprNode rightOperand;

    public NotEqualInstruction(ASTNode node, ASTAdditiveExprNode leftOperand, ASTAdditiveExprNode rightOperand) {
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
        sb.append("not equal ");

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
            value.setBoolValue(doubleValue != doubleValue2);
        } else if (node.getType().is(SuperType.TY_INT)) {
            int intValue = leftOperand.getValue().getIntValue();
            int intValue2 = rightOperand.getValue().getIntValue();
            value.setBoolValue(intValue != intValue2);
        } else if (node.getType().is(SuperType.TY_BOOL)) {
            boolean booleanValue = leftOperand.getValue().isTrue();
            boolean booleanValue2 = rightOperand.getValue().isTrue();
            value.setBoolValue(booleanValue != booleanValue2);
        } else if (node.getType().is(SuperType.TY_STRING)) {
            String stringValue = leftOperand.getValue().getStringValue();
            String stringValue2 = rightOperand.getValue().getStringValue();
            value.setBoolValue(!stringValue.equals(stringValue2));
        }

        node.setValue(value);
    }
}
