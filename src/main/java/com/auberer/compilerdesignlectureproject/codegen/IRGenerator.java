package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTPrintBuiltinCallNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisitor;
import com.auberer.compilerdesignlectureproject.codegen.instructions.CondJumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.JumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.PrintInstruction;
import lombok.Getter;
import lombok.Setter;

public class IRGenerator extends ASTVisitor<IRExprResult> {

  // IR module, which represents the whole program
  @Getter
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

  // ToDo: Insert other visit methods here


  @Override
  public IRExprResult visitIf(ASTIfStmtNode node) {
    // if, continue in currentBlock
    BasicBlock trueBlock = new BasicBlock("trueBlock");
    BasicBlock afterIfBlock = new BasicBlock("afterIfBlock");
    BasicBlock exitBlock = new BasicBlock("exitBlock");

    if (node.getAfterIf() != null) {
      currentBlock.pushInstruction(new CondJumpInstruction(node, node.getCondition(), trueBlock, afterIfBlock));
    } else {
      currentBlock.pushInstruction(new CondJumpInstruction(node, node.getCondition(), trueBlock, exitBlock));
    }
    switchToBlock(trueBlock);
    visit(node.getBody());
    currentBlock.pushInstruction(new JumpInstruction(node, exitBlock));
    if (node.getAfterIf() != null) {
      switchToBlock(afterIfBlock);
      visit(node.getAfterIf());
      currentBlock.pushInstruction(new JumpInstruction(node, exitBlock));
    }
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
