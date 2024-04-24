package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTCasesNode;
import com.auberer.compilerdesignlectureproject.ast.ASTDefaultNode;
import com.auberer.compilerdesignlectureproject.ast.ASTSwitchStmtNode;
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
import static org.mockito.Mockito.times;

public class SwitchStmtNodeTest {
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
    @DisplayName("Test switch statement")
    void testSwitchStmt() {
        // Arrange
        doNothing().when(lexer).advance();
        doNothing().when(lexer).expect(TokenType.TOK_SWITCH);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doNothing().when(parser).parseAssignExpr();
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
        doReturn(new ASTDefaultNode()).when(parser).parseDefault();
        doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
        doReturn(new Token(TokenType.TOK_DEFAULT, "", new CodeLoc(1, 1))).when(lexer).getToken();
        when(parser.parseCases()).thenReturn(new ASTCasesNode());


        // Execute parse method
        ASTSwitchStmtNode switchStmtNode = parser.parseSwitchStmt();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_SWITCH);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(parser, times(1)).parseAssignExpr();
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
        verify(parser, times(1)).parseCases();
        verify(parser, times(1)).parseDefault();
        verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);

        assertNotNull(switchStmtNode);
        assertInstanceOf(ASTSwitchStmtNode.class, switchStmtNode);
    }
}
