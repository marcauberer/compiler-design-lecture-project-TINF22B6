package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTAssignStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTForNode;
import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ForNodeIntegrationTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        Reader reader = new Reader("for (int i = 0; i == 10; i++) {  } cnuf");
        Lexer lexer = new Lexer(reader, false);
        parser = new Parser(lexer);
    }

    // Integration test for the ForNode
    @Test
    void testForNodeParsing() {
        ASTForNode forNode = assertDoesNotThrow(() -> parser.parseForLoop());

        // Use the helper functions to get the body, initialization, condition, and increment
        ASTStmtLstNode body = forNode.getBody();
        ASTAssignStmtNode initialization = forNode.getInitialization();
        ASTAssignStmtNode condition = forNode.getCondition();
        ASTAssignStmtNode increment = forNode.getIncrement();

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
        ASTAssignStmtNode initialization = new ASTAssignStmtNode();
        forNode.addChild(initialization);
        assertEquals(initialization, forNode.getInitialization());
    }

    @Test
    void testGetCondition() {
        ASTForNode forNode = new ASTForNode();
        ASTAssignStmtNode condition = new ASTAssignStmtNode();
        forNode.addChild(new ASTAssignStmtNode()); // initialization
        forNode.addChild(condition);
        assertEquals(condition, forNode.getCondition());
    }

    @Test
    void testGetIncrement() {
        ASTForNode forNode = new ASTForNode();
        ASTAssignStmtNode increment = new ASTAssignStmtNode();
        forNode.addChild(new ASTAssignStmtNode()); // initialization
        forNode.addChild(new ASTAssignStmtNode()); // condition
        forNode.addChild(increment);
        assertEquals(increment, forNode.getIncrement());
    }
}
