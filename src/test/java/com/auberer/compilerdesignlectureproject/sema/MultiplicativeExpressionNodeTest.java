package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplicativeExpressionNodeTest {
    @Test
    @DisplayName("Integration test")
    void multiplicativeExprIntegrationTest() {
        String code = """
                    2 * 3
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        ASTMultiplicativeExprNode astMultiplicativeExprNode = parser.parseMultiplicativeExpression();
        symbolTableBuilder.visitMultiplicativeExpr(astMultiplicativeExprNode);


        assertNotNull(astMultiplicativeExprNode);
        assertInstanceOf(ASTMultiplicativeExprNode.class, astMultiplicativeExprNode);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astMultiplicativeExprNode.operands().getFirst().operand().getExprType());
        assertEquals(2, astMultiplicativeExprNode.operands().getFirst().operand().getIntLit());
        assertEquals(ASTMultiplicativeExprNode.MultiplicativeOperator.MUL, astMultiplicativeExprNode.operatorList.getFirst());
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astMultiplicativeExprNode.operands().getLast().operand().getExprType());
        assertEquals(3, astMultiplicativeExprNode.operands().getLast().operand().getIntLit());
    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong operand type")
    void multiplicativeExprIntegrationTestWrongType() {
        String code = """
                    2 * "a"
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTMultiplicativeExprNode astMultiplicativeExprNode = parser.parseMultiplicativeExpression();
        symbolTableBuilder.visitMultiplicativeExpr(astMultiplicativeExprNode);


        assertNotNull(astMultiplicativeExprNode);
        assertInstanceOf(ASTMultiplicativeExprNode.class, astMultiplicativeExprNode);
        assertEquals(ASTMultiplicativeExprNode.MultiplicativeOperator.MUL, astMultiplicativeExprNode.operatorList.getFirst());

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitMultiplicativeExpr(astMultiplicativeExprNode));
        assertTrue(exception.getMessage().contains("Type mismatch in multiplicative expression"));

    }
}
