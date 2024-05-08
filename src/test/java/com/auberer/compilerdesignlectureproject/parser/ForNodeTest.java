package com.auberer.compilerdesignlectureproject.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.auberer.compilerdesignlectureproject.ast.ASTForNode;
import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class ForNodeTest {

    @Spy
    private Parser parser;

    @Mock
    private Lexer lexer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = new Parser(lexer);
        parser = spy(parser);
    }

    @Test
    @DisplayName("Test for node")
    void testForNode() {
        // Arrange
        doNothing().when(lexer).expect(TokenType.TOK_FOR);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doNothing().when(parser).parseAssignExpr();
        doNothing().when(lexer).expect(TokenType.TOK_SEMICOLON);
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
        doReturn(null).when(parser).parseStmtLst();
        doNothing().when(lexer).expect(TokenType.TOK_RBRACE);

        // Execute parse method
        ASTForNode printForNode = parser.parseForLoop();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_FOR);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(parser, times(3)).parseAssignExpr();
        verify(lexer, times(2)).expect(TokenType.TOK_SEMICOLON);
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
        verify(parser, times(1)).parseStmtLst();
        verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
        assertNotNull(printForNode);
        assertInstanceOf(ASTForNode.class, printForNode);
    }
}