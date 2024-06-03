package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdditiveExpressionNodeTest {
    @Test
    @DisplayName("Integration test")
    void additiveExprIntegrationTest() {
        String code = """
                    1 + 2
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        ASTAdditiveExprNode astAdditiveExprNode = parser.parseAdditiveExpression();
        symbolTableBuilder.visitAdditiveExpr(astAdditiveExprNode);

        assertNotNull(astAdditiveExprNode);
        assertInstanceOf(ASTAdditiveExprNode.class, astAdditiveExprNode);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astAdditiveExprNode.operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals(1, astAdditiveExprNode.operands().getFirst().operands().getFirst().operand().getIntLit());
        assertEquals(ASTAdditiveExprNode.AdditiveOperator.PLUS, astAdditiveExprNode.operatorList.getFirst());
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astAdditiveExprNode.operands().getLast().operands().getFirst().operand().getExprType());
        assertEquals(2, astAdditiveExprNode.operands().getLast().operands().getFirst().operand().getIntLit());
    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong operand type")
    void additiveExprIntegrationTestWrongType() {
        String code = """
                    1 + "a"
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTAdditiveExprNode astAdditiveExprNode = parser.parseAdditiveExpression();
        symbolTableBuilder.visitAdditiveExpr(astAdditiveExprNode);

        assertNotNull(astAdditiveExprNode);
        assertInstanceOf(ASTAdditiveExprNode.class, astAdditiveExprNode);
        assertEquals(ASTAdditiveExprNode.AdditiveOperator.PLUS, astAdditiveExprNode.operatorList.getFirst());

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitAdditiveExpr(astAdditiveExprNode));
        assertTrue(exception.getMessage().contains("Type mismatch in additive expression"));

    }
}
