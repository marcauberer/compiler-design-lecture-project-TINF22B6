package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTAtomicExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTMultiplicativeExprNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MultiplicativeExprNodeTest {
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
    @DisplayName("Test multiplicative expression")
    void testMultiplicativeExpr() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "4", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_MUL, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "2", new CodeLoc(1, 3)));

        // Arrange
        doReturn(null).when(parser).parsePrefixExpression();
        doNothing().when(lexer).expect(TokenType.TOK_MUL);
        doReturn(tokenList.get(1), tokenList.get(1), tokenList.get(1), tokenList.get(2)).when(lexer).getToken();

        // Execute parse method
        ASTMultiplicativeExprNode multiplicativeExprNode = parser.parseMultiplicativeExpression();

        // Assert
        verify(parser, times(2)).parsePrefixExpression();
        verify(lexer, times(1)).expect(TokenType.TOK_MUL);
        assertNotNull(multiplicativeExprNode);
        assertInstanceOf(ASTMultiplicativeExprNode.class, multiplicativeExprNode);
    }

    @Test
    @DisplayName("Integration test")
    void multiplicativeExprIntegrationTest() {
        String code = "2 * 3";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTMultiplicativeExprNode multiplicativeExpr = parser.parseMultiplicativeExpression();

        assertNotNull(multiplicativeExpr);
        assertInstanceOf(ASTMultiplicativeExprNode.class, multiplicativeExpr);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, multiplicativeExpr.operands().getFirst().operand().getExprType());
        assertEquals(2, multiplicativeExpr.operands().getFirst().operand().getIntLit());
        assertEquals(ASTMultiplicativeExprNode.MultiplicativeOperator.MUL, multiplicativeExpr.operatorList.getFirst());
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, multiplicativeExpr.operands().getLast().operand().getExprType());
        assertEquals(3, multiplicativeExpr.operands().getLast().operand().getIntLit());
    }

    @Test
    @DisplayName("Integration test")
    void integrationTestSymbolTableBuilder() {
        String code = "2 * 3";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTMultiplicativeExprNode multiplicativeExpr = parser.parseMultiplicativeExpression();
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        symbolTableBuilder.visitMultiplicativeExpr(multiplicativeExpr);
    }
}
