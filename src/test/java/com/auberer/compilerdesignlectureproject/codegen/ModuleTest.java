package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleTest {

  @Test
  @DisplayName("Dump whole module")
  public void dumpEmptyModule() {
    String input = """
        func empty main()
            return;
        cnuf
        """;

    // Lex and parse
    Module module = compileModule(input);

    // Dump module
    StringBuilder sb = new StringBuilder();
    module.dumpIR(sb);

    String expectedOutput = """
module test.tinf:

function main(): {
fctDef:
  return
}

""";
    assertEquals(expectedOutput, sb.toString());
  }

  static Module compileModule(String input) {
    Reader reader = new Reader(input);
    Lexer lexer = new Lexer(reader, false);
    Parser parser = new Parser(lexer);
    ASTEntryNode ast = parser.parse();

    // Build symbol table
    SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
    symbolTableBuilder.visit(ast);

    // Perform type checking
    TypeChecker typeChecker = new TypeChecker(ast);
    typeChecker.visit(ast);

    // Generate code
    String moduleName = "test.tinf";
    IRGenerator irGenerator = new IRGenerator(moduleName);
    irGenerator.visit(ast);
    return irGenerator.getModule();
  }

}
