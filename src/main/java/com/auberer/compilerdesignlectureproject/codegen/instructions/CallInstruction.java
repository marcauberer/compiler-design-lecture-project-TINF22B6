package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCallParamsNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.codegen.Function;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import lombok.Getter;

import java.util.ListIterator;
import java.util.stream.Collectors;

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
        // Save the current instruction iterator
        ListIterator<Instruction> returnIterator = env.getInstructionIterator();
        // Handle the function call in the interpreter environment
        env.callFunction(returnIterator, function);
    }

    @Override
    public void dumpIR(StringBuilder sb) {
        // call <functionName>(<params>)
        sb.append("call ").append(function.getName()).append("(");
        String params = callParamsNode.getParamsAsLogicNodes().stream().map(astLogicalExprNode -> astLogicalExprNode.getValue().getName()).collect(Collectors.joining(","));
        sb.append(params).append(")");
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": call ");
    }

}