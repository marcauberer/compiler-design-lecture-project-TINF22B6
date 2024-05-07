package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTMultiplicativeExprNode;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class LogicalExprNodeTest {
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
    @DisplayName("Test logical expression")
    void testLogicalExpr() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "4", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_LOGICAL_AND, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "2", new CodeLoc(1, 3)));

        // Arrange
        doReturn(null).when(parser).parseCompareExpression();
        doNothing().when(lexer).expectOneOf(Set.of(TokenType.TOK_LOGICAL_AND, TokenType.TOK_LOGICAL_OR));
        doReturn(tokenList.get(1), tokenList.get(2)).when(lexer).getToken();

        // Execute parse method
        ASTLogicalExprNode logicalExprNode = parser.parseLogicalExpression();

        // Assert
        verify(parser, times(2)).parseCompareExpression();
        verify(lexer, times(1)).expectOneOf(Set.of(TokenType.TOK_LOGICAL_AND, TokenType.TOK_LOGICAL_OR));
        assertNotNull(logicalExprNode);
        assertInstanceOf(ASTLogicalExprNode.class, logicalExprNode);
    }
}
