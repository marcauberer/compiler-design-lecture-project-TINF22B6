package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCaseNode;
import com.auberer.compilerdesignlectureproject.ast.ASTCasesNode;
import com.auberer.compilerdesignlectureproject.ast.ASTDefaultNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.codegen.BasicBlock;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableEntry;
import org.antlr.v4.runtime.Token;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SwitchInstruction extends Instruction{
    private final Value value;
    private final List<BasicBlock> casesBlocks;

    private final List<ASTCaseNode> cases;
    private final BasicBlock defaultBlock;
    public SwitchInstruction(ASTNode node, Value value, List<BasicBlock> casesBlocks, List<ASTCaseNode> cases, BasicBlock defaultBlock) {
        super(node);
        this.value = value;
        this.casesBlocks = casesBlocks;
        this.defaultBlock = defaultBlock;
        this.cases = cases;
    }

    @Override
    public void run(InterpreterEnvironment env) {
        boolean foundCase = false;
        for(int i = 0; i < cases.size(); i++){
            if(Objects.equals(cases.get(i).getCaseLiteral(), value.toString())){
                env.setInstructionIterator(casesBlocks.get(i).getInstructions().listIterator());
            }
        }
        if(!foundCase){
            if(defaultBlock != null) {
                env.setInstructionIterator(defaultBlock.getInstructions().listIterator());
            }
        }
    }

    @Override
    public void dumpIR(StringBuilder sb) {
        sb.append("switch (").append(value.getName()).append(") ");
        List<String> blockNames = casesBlocks.stream().map(BasicBlock::getLabel).toList();
        sb.append(String.join(", ", blockNames));
        if(defaultBlock != null){
            sb.append(", ").append(defaultBlock.getLabel());
        }
    }

    @Override
    public void trace(StringBuilder sb) {
        sb.append(node.getCodeLoc().toString()).append(": switch ");
    }

    @Override
    public boolean isTerminator() {
        return true;
    }
}
