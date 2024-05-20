package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTFctDefNode;
import com.auberer.compilerdesignlectureproject.ast.ASTParamLstNode;
import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
import com.auberer.compilerdesignlectureproject.ast.ASTTypeNode;
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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FunctionDefinitionTest {
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
    @DisplayName("Test function definition")
    void testFunctionDefinition() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_FUNC, "", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_TYPE_INT, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_TYPE_INT, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_TYPE_INT, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_TYPE_INT, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_RPAREN, "", new CodeLoc(1, 3)));
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "", new CodeLoc(1, 4)));

        /*
        func empty myfunction()
            return;
        cnuf
         */

        doNothing().when(lexer).expect(TokenType.TOK_FUNC);
        doReturn(null).when(parser).parseType();
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doReturn(null).when(parser).parseStmtLst();
        doNothing().when(lexer).expect(TokenType.TOK_RETURN);
        doNothing().when(lexer).expect(TokenType.TOK_SEMICOLON);
        doNothing().when(lexer).expect(TokenType.TOK_CNUF);
        doReturn(
            tokenList.get(0),
            tokenList.get(1),
            tokenList.get(2),
            tokenList.get(3),
            tokenList.get(4)
        ).when(lexer).getToken();

        ASTFctDefNode astFctDefNode = parser.parseFctDef();
        assertNotNull(astFctDefNode);

        verify(lexer, times(1)).expect(TokenType.TOK_FUNC);
        verify(parser, times(2)).parseType();
        verify(lexer, times(2)).expect(TokenType.TOK_IDENTIFIER);
        verify(parser, times(1)).parseParamLst();
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
        verify(parser, times(1)).parseStmtLst();
        verify(lexer, times(1)).expect(TokenType.TOK_RETURN);
        verify(lexer, times(1)).expect(TokenType.TOK_SEMICOLON);
        verify(lexer, times(1)).expect(TokenType.TOK_CNUF);
    }

    @Test
    @DisplayName("Integration test for function definition")
    void testIntegrationTestForFunctionCall() {

        String fctDef = "func int myFunc(int x) int i = 17; return x; cnuf";
        Lexer lexer1 = new Lexer(new Reader(fctDef), true);
        Parser parser1 = new Parser(lexer1);
        ASTFctDefNode astFctDefNode = parser1.parseFctDef();
        assertInstanceOf(ASTFctDefNode.class, astFctDefNode);
        assertInstanceOf(ASTTypeNode.class, astFctDefNode.getType());
        assertInstanceOf(ASTParamLstNode.class, astFctDefNode.getParams());
    }
}
