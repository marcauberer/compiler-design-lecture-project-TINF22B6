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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DoWhileLoopNodeTest {

    @Spy
    private Parser parser;

    @Mock
    private Lexer lexer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = new Parser(lexer);
        parser = spy(parser);
    }

    @Test
    @DisplayName("Test do while loop node")
    void testDoWhileLoopNode() {
        // Arrange
        doReturn(new Token(TokenType.TOK_DO, "", new CodeLoc(1, 1))).when(lexer).getToken();
        doNothing().when(lexer).expect(TokenType.TOK_DO);
        doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
        doReturn(null).when(parser).parseStmtLst();
        doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
        doNothing().when(lexer).expect(TokenType.TOK_WHILE);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doReturn(null).when(parser).parseLogicalExpression();
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doNothing().when(lexer).expect(TokenType.TOK_SEMICOLON);

        // Execute parse method
        ASTDoWhileLoopNode doWhileLoopNode = parser.parseDoWhile();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_DO);
        verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
        verify(parser, times(1)).parseStmtLst();
        verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
        verify(lexer, times(1)).expect(TokenType.TOK_WHILE);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(parser, times(1)).parseLogicalExpression();
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_SEMICOLON);
        assertNotNull(doWhileLoopNode);
        assertInstanceOf(ASTDoWhileLoopNode.class, doWhileLoopNode);
    }

    @Test
    void integrationTest() {
        String input = "do {int i = 0; i = i + 1;} while (false) ;";
        Reader reader = new Reader(input);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTDoWhileLoopNode doWhileLoopNode = parser.parseDoWhile();

        ASTStmtLstNode body = doWhileLoopNode.getBody();
        ASTLogicalExprNode condition = doWhileLoopNode.getCondition();
        assert body.getChildren().size() == 2;
        assert body.getChildren().get(0).getChildren().getFirst() instanceof ASTVarDeclNode;
        assert body.getChildren().get(1).getChildren().getFirst() instanceof ASTAssignStmtNode;
        assert condition.getChildren().size() == 1;
        assert condition.getChild(ASTCompareExprNode.class, 0)
                .getChild(ASTAdditiveExprNode.class, 0)
                .getChild(ASTMultiplicativeExprNode.class, 0)
                .getChild(ASTPrefixExprNode.class, 0)
                .getChildren().getFirst() instanceof ASTAtomicExprNode;
    }
}
