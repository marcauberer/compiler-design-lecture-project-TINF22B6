package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfStmtSymbolTableBuilderTest {

  @Test
  public void checkIfStmtSymbolTableContext() {
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

    // Generate Scopes and their SymbolTables
    SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
    symbolTableBuilder.visit(ifStmtNode);

    assertEquals(1, symbolTableBuilder.currentScopes.size());
  }

  @Test
  public void checkElseIfStmtSymbolTableContext() {
    // Create a new Reader object with the given file path
    Reader reader = new Reader("""
        if (true == false) {
            print("XSLT");
        } else {
            print("http://www.jroethig.de/");
        }
        """);

    // Create lexer and parser
    Lexer lexer = new Lexer(reader, false);
    Parser parser = new Parser(lexer);

    // Execute parse method
    ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

    // Generate Scopes and their SymbolTables
    SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
    symbolTableBuilder.visit(ifStmtNode);

    assertEquals(1, symbolTableBuilder.currentScopes.size());
  }
}
