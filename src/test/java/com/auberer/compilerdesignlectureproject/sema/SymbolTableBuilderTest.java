package com.auberer.compilerdesignlectureproject.sema;


import com.auberer.compilerdesignlectureproject.ast.ASTWhileLoopNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SymbolTableBuilderTest {

    @Test
    @DisplayName("Integration test")
    void testWhileLoopIntegrated() {
        String code = "while (1) { int i = 5 + 6; double d = 12.3 + i; i = -13; }";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTWhileLoopNode astWhileLoopNode = parser.parseWhileLoop();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitWhileLoop(astWhileLoopNode);

        assertNotNull(astWhileLoopNode);
    }

}
