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
   String input="""
              if (true == false) {
                  print("XSLT");
              }
              """;

    Module module= compileModule(input);
    StringBuilder sb = new StringBuilder();
    module.dumpIR(sb);
    System.out.printf("%s%n",sb.toString());

    assertEquals(0, 0);
  }

  private static Module compileModule(String input) {
    Reader reader = new Reader(input);
    Lexer lexer = new Lexer(reader, false);
    Parser parser = new Parser(lexer);
    ASTIfStmtNode ast = parser.parseIfStmt();

    // Build symbol table
    SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
    symbolTableBuilder.visit(ast);

    // Perform type checking
    TypeChecker typeChecker = new TypeChecker();
    typeChecker.visitIf(ast);

    // Generate code
    String moduleName = "test.tinf";
    IRGenerator irGenerator = new IRGenerator(moduleName);
    irGenerator.setCurrentBlock(new BasicBlock("root"));
    irGenerator.visit(ast);
    return irGenerator.getModule();
  }

}
