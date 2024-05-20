package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTCallParamsNode;
import com.auberer.compilerdesignlectureproject.ast.ASTFctCallNode;
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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FunctionCallTest {

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
    @DisplayName("Test function call")
    void testFunctionCall() {
        Token identifier = (new Token(TokenType.TOK_IDENTIFIER, "", new CodeLoc(1, 1)));

        doNothing().when(lexer).expect(TokenType.TOK_CALL);
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doReturn(null).when(parser).parseCallParams();
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doReturn(identifier).when(lexer).getToken();

        ASTFctCallNode astFctCallNode = parser.parseFctCall();
        assertNotNull(astFctCallNode);

        verify(parser, times(1)).parseFctCall();
        verify(lexer, times(1)).expect(TokenType.TOK_CALL);
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);

        verify(parser, times(1)).parseCallParams();
    }

    @Test
    @DisplayName("Integration test for function call")
    void testIntegrationTestForFunctionCall() {

        String fctDef = "call myFunc(7);";
        Lexer lexer1 = new Lexer(new Reader(fctDef), true);
        Parser parser1 = new Parser(lexer1);
        ASTFctCallNode astFctCallNode = parser1.parseFctCall();
        assertInstanceOf(ASTFctCallNode.class, astFctCallNode);
        assertInstanceOf(ASTCallParamsNode.class, astFctCallNode.getCallParams());
    }
}
