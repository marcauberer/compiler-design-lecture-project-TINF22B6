package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompareExpressionNodeTest {
    @Test
    @DisplayName("Integration test")
    void compareExprIntegrationTest() {
        String code = """
                    2 != 3
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        ASTCompareExprNode astCompareExprNode = parser.parseCompareExpression();
        symbolTableBuilder.visitCompareExpr(astCompareExprNode);

        assertNotNull(astCompareExprNode);
        assertInstanceOf(ASTCompareExprNode.class, astCompareExprNode);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astCompareExprNode.operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals(2, astCompareExprNode.operands().getFirst().operands().getFirst().operands().getFirst().operand().getIntLit());
        assertEquals(ASTCompareExprNode.CompareOperator.NOT_EQUAL, astCompareExprNode.operator);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astCompareExprNode.operands().getLast().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals(3, astCompareExprNode.operands().getLast().operands().getFirst().operands().getFirst().operand().getIntLit());
    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong operand type")
    void compareExprIntegrationTestWrongType() {
        String code = """
                    2 != "a"
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTCompareExprNode astCompareExprNode = parser.parseCompareExpression();
        symbolTableBuilder.visitCompareExpr(astCompareExprNode);

        assertNotNull(astCompareExprNode);
        assertInstanceOf(ASTCompareExprNode.class, astCompareExprNode);
        assertEquals(ASTCompareExprNode.CompareOperator.NOT_EQUAL, astCompareExprNode.operator);

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitCompareExpr(astCompareExprNode));
        assertTrue(exception.getMessage().contains("Type mismatch in compare expression"));

    }
}
