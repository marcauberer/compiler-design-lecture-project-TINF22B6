package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTCasesNode;
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

public class CompareExprNodeTest {
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
    @DisplayName("Test switch statement cases")
    void testCases() {

        //TODO Token List anpassen
        List<Token> tokenList = new LinkedList<>();
        for(int i = 0; i < 3; i++){
            Token token = new Token(TokenType.TOK_CASE, String.valueOf(i), new CodeLoc(1,1));
            tokenList.add(token);
        }
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "end", new CodeLoc(1, 1)));

        // Arrange

        //TODO Statement Abfrage vervollständigen
        doNothing().when(lexer).advance();
        doNothing().when(parser).parseAdditiveExpression();
        doNothing().when(lexer).expectOneOf(Set.of(TokenType.TOK_EQUAL, TokenType.TOK_NOT_EQUAL));
        doNothing().when(parser).parseAdditiveExpression();


        // Execute parse method
        ASTCompareExprNode compareExprNode = parser.parseCompareExpression();

        // Assert

        //TODO Statement Durchlauf anpassen
        verify(parser, times(3)).parseAdditiveExpression();
        verify(lexer, times(3)).expectOneOf(Set.of(TokenType.TOK_EQUAL, TokenType.TOK_NOT_EQUAL));
        verify(parser, times(3)).parseAdditiveExpression();

        assertNotNull(compareExprNode);
        assertInstanceOf(ASTCompareExprNode.class, compareExprNode);
    }
}
