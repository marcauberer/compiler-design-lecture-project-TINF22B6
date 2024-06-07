package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IfStmtIRGeneratorTest {

  @Test
  @DisplayName("Dump IR")
  public void dumpEmptyModule() {
    // Create a new Reader object with the given file path
   String input="""
              if (true == false) {
                  print("XSLT");
              }
              """;

    Reader reader = new Reader(input);
    Lexer lexer = new Lexer(reader, false);
    Parser parser = new Parser(lexer);
    ASTIfStmtNode ast = parser.parseIfStmt();


    BasicBlock startBlock=new BasicBlock("root");
    IRGenerator irGenerator = new IRGenerator("ifStatement");
    irGenerator.setCurrentBlock(startBlock);
    Function function= new Function("ifStatement");
    function.setEntryBlock(startBlock);
    StringBuilder sb = new StringBuilder();
    function.dumpIR(sb);

    System.out.printf("%s%n",sb.toString());

    assertEquals(sb.toString().trim(), "function ifStatement: {\n}");
  }




}
