package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForLoopIntegrationTest {

    @Test
    public void testSuccessfulForLoop() {
        String input = """
        func int main()
            for (int i = 0; i == 10; i = i + 1) {
                print(i);
            }
        return 0;
         cnuf
        """;

        Reader reader = new Reader(input);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTEntryNode ast = parser.parse();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visit(ast);

        TypeChecker typeChecker = new TypeChecker(ast);
        typeChecker.visit(ast);

        String moduleName = "test.tinf";
        IRGenerator irGenerator = new IRGenerator(moduleName);
        irGenerator.visit(ast);
        Module module = irGenerator.getModule();

        StringBuilder sb = new StringBuilder();
        module.dumpIR(sb);

        assertTrue(sb.toString().contains("loopHead"));
        assertTrue(sb.toString().contains("loopBody"));
        assertTrue(sb.toString().contains("loopEnd"));
    }
}