package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.codegen.instructions.*;
import lombok.Getter;
import lombok.Setter;

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
  public IRExprResult visitForLoop(ASTForNode node) {
    BasicBlock loopHeadBlock = new BasicBlock("loopHead");
    BasicBlock loopBodyBlock = new BasicBlock("loopBody");
    BasicBlock loopEndBlock = new BasicBlock("loopEnd");

    visit(node.getInitialization());

    JumpInstruction jumpToLoopHead = new JumpInstruction(node, loopHeadBlock);
    pushToCurrentBlock(jumpToLoopHead);

    switchToBlock(loopHeadBlock);
    IRExprResult conditionResult = visit(node.getCondition());

    CondJumpInstruction condJumpInstruction = new CondJumpInstruction(node, conditionResult.getValue().getNode(), loopBodyBlock, loopEndBlock);
    pushToCurrentBlock(condJumpInstruction);

    switchToBlock(loopBodyBlock);
    visit(node.getBody());

    visit(node.getIncrement());

    JumpInstruction jumpInstruction = new JumpInstruction(node, loopHeadBlock);
    pushToCurrentBlock(jumpInstruction);

    switchToBlock(loopEndBlock);

    return null;
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
    assert !isBlockTerminated(currentBlock);
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

  @Override
  public IRExprResult visitFctCall(ASTFctCallNode node) {
    //BasicBlock basicBlock = new BasicBlock("fctCall");
    //switchToBlock(basicBlock);
    CallInstruction callInstruction = new CallInstruction(node, module.getFunction(node.getName(), node.getCallParams().getParamsAsLogicNodes().stream().map(ASTNode::getType).toList()), node.getCallParams());
    pushToCurrentBlock(callInstruction);
    //currentBlock = null;
    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitFctDef(ASTFctDefNode node) {
    BasicBlock fctDef = new BasicBlock("fctDef");

    switchToBlock(fctDef);
    if(node.hasParams()){
      visitParamLst(node.getParams());
    }


    List<Function.Parameter> params = node.hasParams()?
            node.getParams().getParamNodes().stream().map((paramNode) -> new Function.Parameter(paramNode.getName(), paramNode.getType())).toList()
            :new ArrayList<>();

    Function function = new Function(node.getName(), params);
    function.setEntryBlock(fctDef);
    module.addFunction(function);

    visitLogic(node.getBody());

    currentBlock = null;
    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitParamLst(ASTParamLstNode node){
    for (ASTParamNode paramNode: node.getParamNodes()){
      visitParam(paramNode);
    }
    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitParam(ASTParamNode node){
    AllocaInstruction allocaInstruction = new AllocaInstruction(node, node.getSymbol());
    pushToCurrentBlock(allocaInstruction);
    StoreInstruction storeParam = new StoreInstruction(node, node.getSymbol());
    pushToCurrentBlock(storeParam);
    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitLogic(ASTLogicNode node) {
    visitStmtLst(node.getBody());
    ReturnInstruction returnInstruction = new ReturnInstruction(node);
    pushToCurrentBlock(returnInstruction);
    return new IRExprResult(null, node, null);
  }
}


