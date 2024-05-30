package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;

public class LogicalExprInstruction extends Instruction {

  private final ASTCompareExprNode leftOperand;
  private final ASTCompareExprNode rightOperand;
  private final ASTLogicalExprNode.LogicalOperator operator;

  public LogicalExprInstruction(ASTLogicalExprNode node, ASTCompareExprNode leftOperand, ASTCompareExprNode rightOperand, ASTLogicalExprNode.LogicalOperator operator) {
    super(node);
    this.leftOperand = leftOperand;
    this.rightOperand = rightOperand;
    this.operator = operator;
  }

  @Override
  public void run(InterpreterEnvironment env) {
    // ToDo(Team 8): Implement
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    switch (operator) {
      case AND:
        sb.append("and ");
        break;
      case OR:
        sb.append("or ");
        break;
    }
    sb.append(leftOperand.getValue().getName());
    sb.append(", ");
    sb.append(rightOperand.getValue().getName());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append("binary operation: logical_expr");
  }
}
