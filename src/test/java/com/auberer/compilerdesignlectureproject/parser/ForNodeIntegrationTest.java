package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.Scope;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

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

    // Unit tests for ASTForNode methods
    @Test
    void testGetBody() {
        ASTForNode forNode = new ASTForNode();
        ASTStmtLstNode body = new ASTStmtLstNode();
        forNode.addChild(body);
        assertEquals(body, forNode.getBody());
    }

    @Test
    void testGetInitialization() {
        ASTForNode forNode = new ASTForNode();
        ASTVarDeclNode initialization = new ASTVarDeclNode();
        forNode.addChild(initialization);
        assertEquals(initialization, forNode.getInitialization());
    }

    @Test
    void testGetCondition() {
        ASTForNode forNode = new ASTForNode();
        ASTLogicalExprNode condition = new ASTLogicalExprNode();
        forNode.addChild(new ASTAssignStmtNode()); // initialization
        forNode.addChild(condition);
        assertEquals(condition, forNode.getCondition());
    }

    @Test
    void testGetIncrement() {
        ASTForNode forNode = new ASTForNode();
        ASTAssignStmtNode increment = new ASTAssignStmtNode();
        forNode.addChild(new ASTAssignStmtNode()); // initialization
        forNode.addChild(new ASTLogicalExprNode()); // condition
        forNode.addChild(increment);
        assertEquals(increment, forNode.getIncrement());
    }
}
