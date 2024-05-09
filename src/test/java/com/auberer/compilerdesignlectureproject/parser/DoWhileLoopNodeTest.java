package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTDoWhileLoopNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class DoWhileLoopNodeTest {

    @Spy
    private Parser parser;

    @Mock
    private Lexer lexer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = new Parser(lexer);
        parser = spy(parser);
    }

    @Test
    @DisplayName("Test do while loop node")
    void testDoWhileLoopNode() {
        // Arrange
        doNothing().when(lexer).advance();
        doNothing().when(lexer).expect(TokenType.TOK_DO);
        doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
        doReturn(null).when(parser).parseStmtLst();
        doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
        doNothing().when(lexer).expect(TokenType.TOK_WHILE);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doReturn(null).when(parser).parseAssignExpr();
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doNothing().when(lexer).expect(TokenType.TOK_SEMICOLON);

        // Execute parse method
        ASTDoWhileLoopNode doWhileLoopNode = parser.parseDoWhile();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_DO);
        verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
        verify(parser, times(1)).parseStmtLst();
        verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
        verify(lexer, times(1)).expect(TokenType.TOK_WHILE);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(parser, times(1)).parseAssignExpr();
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_SEMICOLON);
        assertNotNull(doWhileLoopNode);
        assertInstanceOf(ASTDoWhileLoopNode.class, doWhileLoopNode);
    }
}
