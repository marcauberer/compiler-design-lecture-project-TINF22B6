package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForNodeIntegrationTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        Reader reader = new Reader("for (int i = 0; i == 10; i = i + 1) {  } cnuf");
        Lexer lexer = new Lexer(reader, false);
        parser = new Parser(lexer);
    }

    // Integration test for the ForNode
    @Test
    void testForNodeParsing() {
        SymbolTableBuilder stb = new SymbolTableBuilder();
        ASTForNode forNode = assertDoesNotThrow(() -> parser.parseForLoop());

        // Use the helper functions to get the body, initialization, condition, and increment
        ASTStmtLstNode body = forNode.getBody();
        ASTVarDeclNode initialization = forNode.getInitialization();
        ASTLogicalExprNode condition = forNode.getCondition();
        ASTAssignStmtNode increment = forNode.getIncrement();

        stb.visitForLoop(forNode);

        // Assert that the body, initialization, condition, and increment are not null
        assertNotNull(body);
        assertNotNull(initialization);
        assertNotNull(condition);
        assertNotNull(increment);
    }
}
