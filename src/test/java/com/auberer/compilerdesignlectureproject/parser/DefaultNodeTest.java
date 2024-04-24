package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DefaultNodeTest {
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
    @DisplayName("Test switch statement default")
    void testDefault() {
        // Arrange
        doNothing().when(lexer).advance();
        doNothing().when(lexer).expect(TokenType.TOK_DEFAULT);
        doNothing().when(lexer).expect(TokenType.TOK_COLON);
        doReturn(new ASTStmtLstNode()).when(parser).parseStmtLst();
        doReturn(new Token(TokenType.TOK_IDENTIFIER, "", new CodeLoc(1, 1))).when(lexer).getToken();


        // Execute parse method
        ASTDefaultNode defaultNode = parser.parseDefault();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_DEFAULT);
        verify(lexer, times(1)).expect(TokenType.TOK_COLON);
        verify(parser, times(1)).parseStmtLst();

        assertNotNull(defaultNode);
        assertInstanceOf(ASTDefaultNode.class, defaultNode);
    }
}
