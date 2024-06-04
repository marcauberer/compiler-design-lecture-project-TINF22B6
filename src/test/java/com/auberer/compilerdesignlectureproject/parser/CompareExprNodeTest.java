package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTAtomicExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTCompareExprNode;
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
    @DisplayName("Test compare expression")
    void testCompareExpr() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "4", new CodeLoc(1, 1)));
        tokenList.add(new Token(TokenType.TOK_EQUAL, "", new CodeLoc(1, 2)));
        tokenList.add(new Token(TokenType.TOK_INT_LIT, "2", new CodeLoc(1, 3)));

        // Arrange
        doReturn(null).when(parser).parseAdditiveExpression();
        doNothing().when(lexer).expect(TokenType.TOK_EQUAL);
        doReturn(tokenList.get(1), tokenList.get(1), tokenList.get(1), tokenList.get(2)).when(lexer).getToken();

        // Execute parse method
        ASTCompareExprNode compareExprNode = parser.parseCompareExpression();

        // Assert
        verify(parser, times(2)).parseAdditiveExpression();
        verify(lexer, times(1)).expect(TokenType.TOK_EQUAL);
        assertNotNull(compareExprNode);
        assertInstanceOf(ASTCompareExprNode.class, compareExprNode);
    }

    @Test
    @DisplayName("Integration test")
    void compareExprIntegrationTest() {
        String code = "2 != 3";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTCompareExprNode compareExpr = parser.parseCompareExpression();

        assertNotNull(compareExpr);
        assertInstanceOf(ASTCompareExprNode.class, compareExpr);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, compareExpr.operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals(2, compareExpr.operands().getFirst().operands().getFirst().operands().getFirst().operand().getIntLit());
        assertEquals(ASTCompareExprNode.CompareOperator.NOT_EQUAL, compareExpr.operator);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, compareExpr.operands().getLast().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals(3, compareExpr.operands().getLast().operands().getFirst().operands().getFirst().operand().getIntLit());
    }

    @Test
    @DisplayName("Integration test")
    void integrationTestSymbolTableBuilder() {
        String code = "2 != 3";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTCompareExprNode compareExpr = parser.parseCompareExpression();
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        symbolTableBuilder.visitCompareExpr(compareExpr);
    }
}
