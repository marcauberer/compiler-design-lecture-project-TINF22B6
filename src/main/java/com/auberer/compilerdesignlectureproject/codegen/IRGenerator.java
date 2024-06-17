package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.codegen.instructions.CondJumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.JumpInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.PrintInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.*;
import com.auberer.compilerdesignlectureproject.sema.SemaError;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IRGenerator extends ASTVisitor<IRExprResult> {

  // IR module, which represents the whole program

  private final Module module;

  @Getter
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

    CondJumpInstruction condJumpInstruction = new CondJumpInstruction(node, conditionResult.getNode(), loopBodyBlock, loopEndBlock);
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
    // Visit expression to print
    visit(node.logicalExpr());

    // Create print instruction and append it to the current BasicBlock
    PrintInstruction printInstruction = new PrintInstruction(node);
    pushToCurrentBlock(printInstruction);

    return new IRExprResult(null, node, null);
  }

  public IRExprResult visitAssignStmt(ASTAssignStmtNode node) {
    visit(node.getLogicalExpr());

    if (node.isAssignment()) {
      StoreInstruction storeInstruction = new StoreInstruction(node.getLogicalExpr(), node.getCurrentSymbol());
      pushToCurrentBlock(storeInstruction);
      return new IRExprResult(node.getCurrentSymbol().getValue(), node, node.getCurrentSymbol());
    }

    return new IRExprResult(node.getLogicalExpr().getValue(), node, null);
  }

  public IRExprResult visitVarDecl(ASTVarDeclNode node) {
    AllocaInstruction allocaInstruction = new AllocaInstruction(node, node.getCurrentSymbol());
    pushToCurrentBlock(allocaInstruction);

    if (node.isHasLogicalExpr()) {
      visit(node.getLogicalExpr());
      StoreInstruction storeInstruction = new StoreInstruction(node, node.getCurrentSymbol());
      pushToCurrentBlock(storeInstruction);
    }

    return new IRExprResult(node.getValue(), node, node.getCurrentSymbol());
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
  public IRExprResult visitIf(ASTIfStmtNode node) {
    // if, continue in currentBlock
    BasicBlock thenBlock = new BasicBlock("if.then");
    BasicBlock elseBlock = new BasicBlock("if.else");
    BasicBlock exitBlock = new BasicBlock("if.exit");

    // Visit condition in original block
    ASTLogicalExprNode condition = node.getCondition();
    visit(condition);
    pushToCurrentBlock(new CondJumpInstruction(node, condition, thenBlock, node.isHasAfterIf() ? elseBlock : exitBlock));

    // Fill then block
    switchToBlock(thenBlock);
    visit(node.getBody());
    pushToCurrentBlock(new JumpInstruction(node, exitBlock));

    if (node.isHasAfterIf()) {
      // Fill else block
      switchToBlock(elseBlock);
      visit(node.getAfterIf());
      pushToCurrentBlock(new JumpInstruction(node, exitBlock));
    }

    // Switch to exit block
    switchToBlock(exitBlock);

    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitLogicalExpr(ASTLogicalExprNode node) {
    List<ASTCompareExprNode> operandsList = node.operands();
    if (operandsList.size() == 1)
      return visit(operandsList.getFirst());
    List<ASTLogicalExprNode.LogicalOperator> operatorsList = node.getOperatorList();

    // Visit the first operand
    visit(operandsList.getFirst());

    for (int i = 1; i < operandsList.size(); i++) {
      visit(operandsList.get(i));
      if (operatorsList.get(i - 1) == ASTLogicalExprNode.LogicalOperator.AND) {
        AndInstruction andInstruction = new AndInstruction(node, operandsList.get(i - 1), operandsList.get(i));
        pushToCurrentBlock(andInstruction);
      } else if (operatorsList.get(i - 1) == ASTLogicalExprNode.LogicalOperator.OR) {
        OrInstruction orInstruction = new OrInstruction(node, operandsList.get(i - 1), operandsList.get(i));
        pushToCurrentBlock(orInstruction);
      }
    }

    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitCompareExpr(ASTCompareExprNode node) {
    List<ASTAdditiveExprNode> operands = node.operands();
    if (operands.size() == 1)
      return visit(operands.getFirst());

    visit(operands.get(0));
    visit(operands.get(1));

    if (node.operator == ASTCompareExprNode.CompareOperator.EQUAL) {
      EqualInstruction equalInstruction = new EqualInstruction(node, operands.get(0), operands.get(1));
      pushToCurrentBlock(equalInstruction);
    } else if (node.operator == ASTCompareExprNode.CompareOperator.NOT_EQUAL) {
      NotEqualInstruction notEqualInstruction = new NotEqualInstruction(node, operands.get(0), operands.get(1));
      pushToCurrentBlock(notEqualInstruction);
    }

    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitAdditiveExpr(ASTAdditiveExprNode node) {
    List<ASTMultiplicativeExprNode> operandsList = node.operands();
    if (operandsList.size() == 1)
      return visit(operandsList.getFirst());
    List<ASTAdditiveExprNode.AdditiveOperator> operatorsList = node.operatorList;

    // Visit the first operand
    visit(operandsList.getFirst());

    for (int i = 1; i < operandsList.size(); i++) {
      visit(operandsList.get(i));
      if (operatorsList.get(i - 1) == ASTAdditiveExprNode.AdditiveOperator.PLUS) {
        PlusInstruction plusInstruction = new PlusInstruction(node, operandsList.get(i - 1), operandsList.get(i));
        pushToCurrentBlock(plusInstruction);
      } else if (operatorsList.get(i - 1) == ASTAdditiveExprNode.AdditiveOperator.MINUS) {
        MinusInstruction minusInstruction = new MinusInstruction(node, operandsList.get(i - 1), operandsList.get(i));
        pushToCurrentBlock(minusInstruction);
      }
    }

    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitMultiplicativeExpr(ASTMultiplicativeExprNode node) {
    List<ASTPrefixExprNode> operandsList = node.operands();
    if (operandsList.size() == 1)
      return visit(operandsList.getFirst());
    List<ASTMultiplicativeExprNode.MultiplicativeOperator> operatorsList = node.operatorList;

    // Visit the first operand
    visit(operandsList.getFirst());

    for (int i = 1; i < operandsList.size(); i++) {
      visit(operandsList.get(i));
      if (operatorsList.get(i - 1) == ASTMultiplicativeExprNode.MultiplicativeOperator.MUL) {
        MultiplicativeInstruction multiplicativeInstruction = new MultiplicativeInstruction(node, operandsList.get(i - 1), operandsList.get(i));
        pushToCurrentBlock(multiplicativeInstruction);
      } else if (operatorsList.get(i - 1) == ASTMultiplicativeExprNode.MultiplicativeOperator.DIV) {
        DivisionInstruction divisionInstruction = new DivisionInstruction(node, operandsList.get(i - 1), operandsList.get(i));
        pushToCurrentBlock(divisionInstruction);
      }
    }

    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitPrefixExpr(ASTPrefixExprNode node) {
    ASTAtomicExprNode operand = node.operand();
    visit(operand);

    if (node.operator == ASTPrefixExprNode.PrefixOperator.PLUS) {
      PrefixPlusInstruction prefixPlusInstruction = new PrefixPlusInstruction(node, operand);
      pushToCurrentBlock(prefixPlusInstruction);
    } else if (node.operator == ASTPrefixExprNode.PrefixOperator.MINUS) {
      PrefixMinusInstruction prefixMinusInstruction = new PrefixMinusInstruction(node, operand);
      pushToCurrentBlock(prefixMinusInstruction);
    }

    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitAtomicExpr(ASTAtomicExprNode node) {
    ASTAtomicExprNode.AtomicType atomicOperator = node.getExprType();

    switch (atomicOperator) {
      case INT_LIT -> node.getValue().setIntValue(node.getIntLit());
      case DOUBLE_LIT -> node.getValue().setDoubleValue(node.getDoubleLit());
      case BOOL_LIT -> node.getValue().setBoolValue(node.isBoolLit());
      case STRING_LIT -> node.getValue().setStringValue(node.getStringLit());
      case IDENTIFIER -> {
        LoadInstruction loadInstruction = new LoadInstruction(node, node.getCurrentSymbol());
        pushToCurrentBlock(loadInstruction);
        return new IRExprResult(node.getCurrentSymbol().getValue(), node, node.getCurrentSymbol());
      }
      case FCT_CALL -> {
        IRExprResult exprResult = visitFctCall(node.getFctCall());
        node.setValue(exprResult.getValue());
        return exprResult;
      }
      case PRINT_BUILTIN_CALL -> {
        IRExprResult exprResult = visitPrintBuiltin(node.getPrintCall());
        node.setValue(exprResult.getValue());
        return exprResult;
      }
      case LOGICAL_EXPR -> {
        IRExprResult exprResult = visitLogicalExpr(node.getLogicalExpr());
        node.setValue(exprResult.getValue());
        return exprResult;
      }
      default -> throw new SemaError(node, "Unknown atomic operator: " + atomicOperator);
    }

    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitSwitchStmt(ASTSwitchStmtNode node) {

    BasicBlock defaultBlock = null;

    if(node.getDefault() != null){
      defaultBlock = new BasicBlock("switch-default");
    }

    List<BasicBlock> casesBlocks = new ArrayList<>();
    for(int i = 0; i < node.getCases().size(); i++){
      casesBlocks.add(new BasicBlock("switch-case " + node.getCases().get(i).getCaseLiteral()));
    }

    BasicBlock endBlock = new BasicBlock("switch-end");

    SwitchInstruction switchInstruction = new SwitchInstruction(node, node.getLogicalExpr().getValue(), casesBlocks, node.getCases(), defaultBlock);
    pushToCurrentBlock(switchInstruction);


    for(int i = 0; i < casesBlocks.size(); i++){
      JumpInstruction caseJumpToEnd = new JumpInstruction(node.getCases().get(i), endBlock);
      BasicBlock b = casesBlocks.get(i);
      switchToBlock(b);
      visitCase(node.getCases().get(i));
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

  @Override
  public IRExprResult visitFctCall(ASTFctCallNode node) {
    CallInstruction callInstruction = new CallInstruction(node, module.getFunction(node.getName(), node.getCallParams().getParamsAsLogicNodes().stream().map(ASTNode::getType).toList()), node.getCallParams());
    pushToCurrentBlock(callInstruction);
    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitFctDef(ASTFctDefNode node) {
    BasicBlock entryBlock = new BasicBlock("entry");
    switchToBlock(entryBlock);

    if (node.hasParams()) {
      visitParamLst(node.getParams());
    }

    List<Function.Parameter> params = node.hasParams() ?
        node.getParams().getParamNodes().stream().map((paramNode) -> new Function.Parameter(paramNode.getName(), paramNode.getType())).toList()
        : new ArrayList<>();

    Function function = new Function(node.getName(), params);
    function.setEntryBlock(entryBlock);
    module.addFunction(function);

    visitLogic(node.getBody());

    finalizeFunction();
    return new IRExprResult(node.getValue(), node, null);
  }

  @Override
  public IRExprResult visitParamLst(ASTParamLstNode node) {
    for (ASTParamNode paramNode : node.getParamNodes()) {
      visitParam(paramNode);
    }
    return new IRExprResult(null, node, null);
  }

  @Override
  public IRExprResult visitParam(ASTParamNode node) {
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
