package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.codegen.instructions.CondJumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.JumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.PrintInstruction;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.codegen.instructions.*;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IRGenerator extends ASTVisitor<IRExprResult> {

  // IR module, which represents the whole program
  private final Module module;
  // The basic block, which is currently the insert point for new instructions
  @Setter
  private BasicBlock currentBlock = null;

  public IRGenerator(String moduleName) {
    module = new Module(moduleName);
  }

  @Override
  public IRExprResult visitEntry(ASTEntryNode node) {
    // We did not enter a function yet, so the current block has to be null
    assert currentBlock == null;

    // Visit children
    visitChildren(node);

    assert currentBlock == null;
    return null;
  }

  @Override
  public IRExprResult visitPrintBuiltin(ASTPrintBuiltinCallNode node) {
    // Create print instruction and append it to the current BasicBlock
    PrintInstruction printInstruction = new PrintInstruction(node);
    pushToCurrentBlock(printInstruction);

    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitWhileLoop(ASTWhileLoopNode node) {
    BasicBlock conditionBlock = new BasicBlock("while.cond");
    BasicBlock bodyBlock = new BasicBlock("while.body");
    BasicBlock exitBlock = new BasicBlock("while.exit");

    JumpInstruction jump = new JumpInstruction(node, conditionBlock);
    pushToCurrentBlock(jump);

    switchToBlock(conditionBlock);
    visitLogicalExpr(node.getCondition());
    CondJumpInstruction condJump = new CondJumpInstruction(node, node.getCondition(), bodyBlock, exitBlock);
    pushToCurrentBlock(condJump);
    switchToBlock(bodyBlock);

    visitStmtLst(node.getBody());
    jump = new JumpInstruction(node, conditionBlock);
    pushToCurrentBlock(jump);
    switchToBlock(conditionBlock);

    switchToBlock(exitBlock);
    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitDoWhileLoop(ASTDoWhileLoopNode node) {
    BasicBlock doWhileBlock = new BasicBlock("do_while.body");
    BasicBlock endDoWhileBlock = new BasicBlock("do_while.exit");
    CondJumpInstruction condJumpInstruction = new CondJumpInstruction(node, node.getCondition(), doWhileBlock, endDoWhileBlock);

    JumpInstruction jumpInstruction = new JumpInstruction(node, doWhileBlock);
    currentBlock.pushInstruction(jumpInstruction);

    switchToBlock(doWhileBlock);
    visitStmtLst(node.getBody());
    doWhileBlock.pushInstruction(condJumpInstruction);
    switchToBlock(endDoWhileBlock);

    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitSwitchStmt(ASTSwitchStmtNode node) {

    BasicBlock defaultBlock = null;

    if(node.getDefault() != null){
      defaultBlock = new BasicBlock("switch-default");
    }

    List<BasicBlock> casesBlocks = new ArrayList<>();
    for(int i = 0; i < node.getCase().size(); i++){
      casesBlocks.add(new BasicBlock("switch-case " + node.getCase().get(i).getCaseLiteral()));
    }

    BasicBlock endBlock = new BasicBlock("switch-end");

    SwitchInstruction switchInstruction = new SwitchInstruction(node, node.getLogicalExpr().getValue(), casesBlocks, node.getCase(), defaultBlock);
    pushToCurrentBlock(switchInstruction);


    for(int i = 0; i < casesBlocks.size(); i++){
      JumpInstruction caseJumpToEnd = new JumpInstruction(node.getCase().get(i), endBlock);
      BasicBlock b = casesBlocks.get(i);
      switchToBlock(b);
      visitCase(node.getCase().get(i));
      b.pushInstruction(caseJumpToEnd);
    }

    if(defaultBlock != null) {
      switchToBlock(defaultBlock);
      visitDefault(node.getDefault());
      JumpInstruction defaultJumpToEnd = new JumpInstruction(node.getDefault(), endBlock);
      defaultBlock.pushInstruction(defaultJumpToEnd);
    }

    switchToBlock(endBlock);

    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitCase(ASTCaseNode node){
    visitStmtLst(node.getStmtList());

    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitCases(ASTCasesNode node) {

    String caseName = currentBlock.getLabel().substring(12);
    int index = node.findCaseIndex(caseName);

    visitStmtLst(node.getStmtLists().get(index));

    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitDefault(ASTDefaultNode node) {

    visitStmtLst(node.getStmtList());

    return new IRExprResult(null, node, null);
  }


  // ToDo: Insert other visit methods here

  /**
   * Can be used to set the instruction insert point to a specific block
   *
   * @param targetBlock Block to switch to
   */
  private void switchToBlock(BasicBlock targetBlock) {
    assert targetBlock != null;

    // Check if the old block was terminated
    assert currentBlock == null || isBlockTerminated(currentBlock);
    // Set insert point to the new basic block
    currentBlock = targetBlock;
  }

  /**
   * Finalizes the IR of the current function by setting the current block to null
   */
  private void finalizeFunction() {
    assert currentBlock != null;
    assert isBlockTerminated(currentBlock);
    currentBlock = null;
  }

  /**
   * Appends the given instruction to the current block
   *
   * @param instruction Instruction to append
   */
  private void pushToCurrentBlock(Instruction instruction) {
    assert instruction != null;
    assert currentBlock != null;
    assert !isBlockTerminated(currentBlock);

    // Push to the back of the current block
    currentBlock.pushInstruction(instruction);
  }

  /**
   * Checks if the given block is terminated
   *
   * @param block Block to check
   * @return True if the block is terminated
   */
  private boolean isBlockTerminated(BasicBlock block) {
    return !block.getInstructions().isEmpty() && block.getInstructions().getLast().isTerminator();
  }

}
