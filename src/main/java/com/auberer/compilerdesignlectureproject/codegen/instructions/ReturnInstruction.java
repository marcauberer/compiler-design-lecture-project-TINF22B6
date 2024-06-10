package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTLogicNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class ReturnInstruction extends Instruction {

  private final ASTLogicalExprNode returnExpr;

  public ReturnInstruction(ASTLogicNode logicNode) {
    super(logicNode);
    returnExpr = logicNode.getReturnNode();
  }

  @Override
  public void run(InterpreterEnvironment env) {
    // Return from the current function and set the instruction iterator to the instruction after the call
    env.returnFromFunction();
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("return ");
    if (returnExpr != null)
      sb.append(returnExpr.getValue().getName());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append(node.getCodeLoc().toString()).append(": return");
  }

  @Override
  public boolean isTerminator() {
    return true;
  }
}