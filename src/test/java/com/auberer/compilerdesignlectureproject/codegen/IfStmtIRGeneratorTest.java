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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class IfStmtIRGeneratorTest {

  @Test
  @DisplayName("Dump IR")
  public void dumpEmptyModule() {
    // Create a new Reader object with the given file path
    Reader reader = new Reader("""
              if (true == false) {
                  print("XSLT");
              }
              """);

    // Create lexer and parser
    Lexer lexer = new Lexer(reader, false);
    Parser parser = new Parser(lexer);

    // Execute parse method
    ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

    IRGenerator generator = new IRGenerator("asdf");
    generator.setCurrentBlock(new BasicBlock(""));
    generator.visitIf(ifStmtNode);

    StringBuilder builder = new StringBuilder();
    generator.getModule().dumpIR(builder);

    assertEquals(builder.toString(), "");

    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIfStmtNode.class, ifStmtNode);
    assertInstanceOf(ASTLogicalExprNode.class, ifStmtNode.getCondition());
    assertInstanceOf(ASTStmtLstNode.class, ifStmtNode.getBody());
  }

}
