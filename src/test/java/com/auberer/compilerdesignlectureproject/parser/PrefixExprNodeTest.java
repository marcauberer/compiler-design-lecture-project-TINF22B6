package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTPrefixExprNode;
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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PrefixExprNodeTest {
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
    @DisplayName("Test prefix expression")
    void testPrefixExpr() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_MINUS, "", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "4", new CodeLoc(1, 2)));

        // Arrange
        doReturn(null).when(parser).parseAtomicExpression();
        doNothing().when(lexer).expect(TokenType.TOK_MINUS);
        doReturn(tokenList.get(0), tokenList.get(0), tokenList.get(0), tokenList.get(1)).when(lexer).getToken();

        // Execute parse method
        ASTPrefixExprNode prefixExprNode = parser.parsePrefixExpression();

        // Assert
        verify(parser, times(1)).parseAtomicExpression();
        verify(lexer, times(1)).expect(TokenType.TOK_MINUS);
        assertNotNull(prefixExprNode);
        assertInstanceOf(ASTPrefixExprNode.class, prefixExprNode);
    }
}
