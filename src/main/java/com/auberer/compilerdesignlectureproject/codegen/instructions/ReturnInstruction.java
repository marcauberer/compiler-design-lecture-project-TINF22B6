package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTLogicNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class ReturnInstruction extends Instruction {

  private final ASTLogicalExprNode returnExpr;

  public ReturnInstruction(ASTLogicNode logicNode) {
    super(logicNode);
    returnExpr = logicNode.logicalExpr();
  }

  @Override
  public void run(InterpreterEnvironment env) {
    // ToDo(Team 6): Implement
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("return ");
    if (returnExpr != null)
      sb.append(returnExpr.getValue().getName());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append("return");
  }
}
