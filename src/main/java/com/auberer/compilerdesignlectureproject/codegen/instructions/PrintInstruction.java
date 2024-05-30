package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTPrintBuiltinCallNode;
import com.auberer.compilerdesignlectureproject.interpreter.InterpreterEnvironment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintInstruction extends Instruction {

  ASTLogicalExprNode exprToPrint;

  public PrintInstruction(ASTPrintBuiltinCallNode node) {
    super(node);
    exprToPrint = node.logicalExpr();
  }

  @Override
  public void run(InterpreterEnvironment env) {
    System.out.println(exprToPrint.getValue().toString());
  }

  @Override
  public void dumpIR(StringBuilder sb) {
    sb.append("print ").append(exprToPrint.getValue().getName());
  }

  @Override
  public void trace(StringBuilder sb) {
    sb.append("builtin function: print");
  }
}
