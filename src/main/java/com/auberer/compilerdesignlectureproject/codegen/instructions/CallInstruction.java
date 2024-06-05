package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCallParamsNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.codegen.Function;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import lombok.Getter;

@Getter
public class CallInstruction extends Instruction {
    private final Function function;
    private final ASTCallParamsNode callParamsNode;

    public CallInstruction(ASTNode node, Function function, ASTCallParamsNode callParamsNode) {
        super(node);
        this.function = function;
        this.callParamsNode = callParamsNode;
    }

    @Override
    public void run(InterpreterEnvironment env) {
        env.setInstructionIterator(function.getEntryBlock().getInstructions().listIterator());
    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("call ").append(function.getName()).append("(").append(callParamsNode.getParamsAsLogicNodes()).append(")");
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": call ");
    }

}
