package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTAssignStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignStmtNodeTest {
    @Spy
    private Parser parser; // Use spy to allow partial mocking

    @Mock
    private Lexer lexer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = new Parser(lexer);
        parser = spy(parser);
    }

    @Test
    @DisplayName("Test assign statement without optional")
    void testAssignStmtWithoutOptional() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_INVALID, "", new CodeLoc(1, 1)));

        doReturn(tokenList.getFirst()).when(lexer).getToken();
        doReturn(null).when(parser).parseLogicalExpression();

        // Execute parse method
        ASTAssignStmtNode assignStmt = parser.parseAssignStmt();

        // Assert
        verify(lexer, times(2)).getToken();
        verify(parser, times(1)).parseLogicalExpression();
        assertNotNull(assignStmt);
        assertInstanceOf(ASTAssignStmtNode.class, assignStmt);
        // Ensure the variable name is empty
        assertNull(assignStmt.getVariableName());
    }

    @Test
    @DisplayName("Test assign statement with optional")
    void testAssignStmtWithOptional() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "xyz", new CodeLoc(1, 1)));

        doReturn(tokenList.getFirst()).when(lexer).getToken();
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doNothing().when(lexer).expect(TokenType.TOK_ASSIGN);
        doReturn(null).when(parser).parseLogicalExpression();

        // Execute parse method
        ASTAssignStmtNode assignStmt = parser.parseAssignStmt();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        verify(lexer, times(1)).expect(TokenType.TOK_ASSIGN);
        verify(parser, times(1)).parseLogicalExpression();
        assertNotNull(assignStmt);
        assertInstanceOf(ASTAssignStmtNode.class, assignStmt);
        // Ensure the variable name is correct
        assertEquals("xyz", assignStmt.getVariableName());
    }
}