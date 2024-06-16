package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTCaseNode;
import com.auberer.compilerdesignlectureproject.ast.ASTCasesNode;
import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
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

public class CaseNodeTest {
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
    @DisplayName("Test switch statement case")
    void testCase() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_CASE, "1", new CodeLoc(1,1)));
        tokenList.add(new Token(TokenType.TOK_CASE, "2", new CodeLoc(1,2)));
        tokenList.add(new Token(TokenType.TOK_CASE, "3", new CodeLoc(1,3)));
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "end", new CodeLoc(1, 4)));

        // Arrange
        doNothing().when(lexer).advance();
        doNothing().when(lexer).expect(TokenType.TOK_CASE);
        doNothing().when(lexer).expectOneOf(Set.of(TokenType.TOK_INT_LIT, TokenType.TOK_DOUBLE_LIT, TokenType.TOK_STRING_LIT));
        doNothing().when(lexer).expect(TokenType.TOK_COLON);
        doReturn(mock(ASTStmtLstNode.class)).when(parser).parseStmtLst();
        doReturn(new Token(TokenType.TOK_CASE, "1", new CodeLoc(1,1))).when(lexer).getToken();

        // Execute parse method
        ASTCaseNode caseNode = parser.parseCase();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_CASE);
        verify(lexer, times(1)).expectOneOf(Set.of(TokenType.TOK_INT_LIT, TokenType.TOK_DOUBLE_LIT, TokenType.TOK_STRING_LIT));
        verify(lexer, times(1)).expect(TokenType.TOK_COLON);
        verify(parser, times(1)).parseStmtLst();

        assertNotNull(caseNode);
        assertInstanceOf(ASTCaseNode.class, caseNode);
    }
}
