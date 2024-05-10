package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTFctCallNode;
import com.auberer.compilerdesignlectureproject.ast.ASTFctDefNode;
import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
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
    @DisplayName("Test logical expression")
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

        // todo: set expected calls to 1, when assign expression node is implemented
        verify(parser, times(0)).parseCallParams();
    }

}
