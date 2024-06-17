package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrefixExpressionNodeTest {
    @Test
    @DisplayName("Integration test")
    void prefixExprIntegrationTest() {
        String code = """
                    -4
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        ASTPrefixExprNode astPrefixExprNode = parser.parsePrefixExpression();
        symbolTableBuilder.visitPrefixExpr(astPrefixExprNode);

        assertNotNull(astPrefixExprNode);
        assertInstanceOf(ASTPrefixExprNode.class, astPrefixExprNode);
        assertEquals(ASTPrefixExprNode.PrefixOperator.MINUS , astPrefixExprNode.operator);
        assertEquals(ASTAtomicExprNode.AtomicType.INT_LIT, astPrefixExprNode.operand().getExprType());
        assertEquals(4, astPrefixExprNode.operand().getIntLit());
    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong operand type")
    void prefixIntegrationTestExceptionWrongType() {
        String code = """
                    -false
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTPrefixExprNode astPrefixExprNode = parser.parsePrefixExpression();
        symbolTableBuilder.visitPrefixExpr(astPrefixExprNode);

        assertNotNull(astPrefixExprNode);
        assertInstanceOf(ASTPrefixExprNode.class, astPrefixExprNode);
        assertEquals(ASTPrefixExprNode.PrefixOperator.MINUS , astPrefixExprNode.operator);

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitPrefixExpr(astPrefixExprNode));
        assertTrue(exception.getMessage().contains("Type mismatch in prefix expression"));

    }
}
