package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTVarDeclNode;
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

public class VarDeclNodeTest {

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
    @DisplayName("Test assign expression without optional")
    void testAssignExprWithoutOptional() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "variable", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_INVALID, "", new CodeLoc(1, 2)));

        doReturn(null).when(parser).parseType();
        doReturn(tokenList.get(0), tokenList.get(0), tokenList.get(0), tokenList.get(1)).when(lexer).getToken();
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);

        // Execute parse method
        ASTVarDeclNode printVarDeclNode = parser.parseVarDecl();

        // Assert
        verify(parser, times(1)).parseType();
        verify(lexer, times(3)).getToken();
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        assertNotNull(printVarDeclNode);
        assertInstanceOf(ASTVarDeclNode.class, printVarDeclNode);
        // Check if the variable name is correct
        assertEquals("variable", printVarDeclNode.getVariableName());
    }

    @Test
    @DisplayName("Test assign expression with optional")
    void testAssignExprWithOptional() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "xyz", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_ASSIGN, "", new CodeLoc(1, 1)));

        doReturn(null).when(parser).parseType();
        doReturn(tokenList.get(0), tokenList.get(0), tokenList.get(1)).when(lexer).getToken();
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doNothing().when(lexer).expect(TokenType.TOK_ASSIGN);
        doReturn(null).when(parser).parseAssignExpr();

        // Execute parse method
        ASTVarDeclNode printVarDeclNode = parser.parseVarDecl();

        // Assert
        verify(parser, times(1)).parseType();
        verify(lexer, times(3)).getToken();
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        verify(lexer, times(1)).expect(TokenType.TOK_ASSIGN);
        verify(parser, times(1)).parseAssignExpr();
        assertNotNull(printVarDeclNode);
        assertInstanceOf(ASTVarDeclNode.class, printVarDeclNode);
        // Check if the variable name is correct
        assertEquals("xyz", printVarDeclNode.getVariableName());
    }
}