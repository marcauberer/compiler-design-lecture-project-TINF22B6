package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.codegen.instructions.*;
import lombok.Getter;

public class IRGenerator extends ASTVisitor<IRExprResult> {

  // IR module, which represents the whole program
  @Getter
  private final Module module;
  // The basic block, which is currently the insert point for new instructions
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

  // ToDo: Insert other visit methods here
  public IRExprResult visitAssignStmt(ASTAssignStmtNode node) {
    IRExprResult logicalExpr = visit(node.getLogicalExpr());

    StoreInstruction storeInstruction = new StoreInstruction(logicalExpr.getNode(), node.getCurrentSymbol());
    pushToCurrentBlock(storeInstruction);
      return new IRExprResult(node.getCurrentSymbol().getValue(), node, node.getCurrentSymbol());
  }

  public IRExprResult VarDecl(ASTVarDeclNode node) {
    IRExprResult datatype = visit(node.getDataType());


    AllocaInstruction instruction = new AllocaInstruction(datatype.getNode(), node.getCurrentSymbol());
    pushToCurrentBlock(instruction);
    if (node.getCurrentSymbol().isUsed()) {
      StoreInstruction instruction1 = new StoreInstruction(datatype.getNode(), node.getCurrentSymbol());
      pushToCurrentBlock(instruction1);
    }
      return new IRExprResult(datatype.getValue(), node, node.getCurrentSymbol());
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
    assert isBlockTerminated(currentBlock);

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
