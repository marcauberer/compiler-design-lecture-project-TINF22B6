package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

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
        doReturn(null).when(parser).parseLogicalExpression();
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
        doReturn(new ASTDefaultNode()).when(parser).parseDefault();
        doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
        doReturn(new Token(TokenType.TOK_DEFAULT, "", new CodeLoc(1, 1))).when(lexer).getToken();
        when(parser.parseCase()).thenReturn(new ASTCaseNode());


        // Execute parse method
        ASTSwitchStmtNode switchStmtNode = parser.parseSwitchStmt();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_SWITCH);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(parser, times(1)).parseLogicalExpression();
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
        verify(parser, times(1)).parseCase();
        verify(parser, times(1)).parseDefault();
        verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);

        assertNotNull(switchStmtNode);
        assertInstanceOf(ASTSwitchStmtNode.class, switchStmtNode);
    }

    @Test
    @DisplayName("Integration test")
    void switchIntegrationTest() {
        String code = "switch (a) { case 1: int i = 0; case 2: int i = 1; default: int i = 5; } ";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTSwitchStmtNode astSwitchStmt = parser.parseSwitchStmt();

        assertNotNull(astSwitchStmt);
        assertInstanceOf(ASTSwitchStmtNode.class, astSwitchStmt);
        assertInstanceOf(List.class, astSwitchStmt.getCase());
        assertInstanceOf(ASTDefaultNode.class, astSwitchStmt.getDefault());
        assert(astSwitchStmt.getCase().size() == 2);
    }
}
