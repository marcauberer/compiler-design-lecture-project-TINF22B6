package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalExpressionNodeTest {
    @Test
    @DisplayName("Integration test")
    void logicalExprIntegrationTest() {
        String code = """
                        a = true;
                        b = true;
                     	c = a&&b;
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTLogicalExprNode astLogicalExprNode = parser.parseLogicalExpression();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        // Fake the symbol table entry for a,b,c
        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("a", astLogicalExprNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("b", astLogicalExprNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("c", astLogicalExprNode);

        symbolTableBuilder.visitLogicalExpr(astLogicalExprNode);

        // Set type for a,b,c
        SymbolTableEntry entryA = scope.lookupSymbolStrict("a", astLogicalExprNode);
        entryA.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryB = scope.lookupSymbolStrict("b", astLogicalExprNode);
        entryB.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryC = scope.lookupSymbolStrict("c", astLogicalExprNode);
        entryC.updateType(new Type(SuperType.TY_BOOL));

        ExprResult result = typeChecker.visitLogicalExpr(astLogicalExprNode);
        assertTrue(result.getType().is(SuperType.TY_BOOL));
        assertNotNull(astLogicalExprNode);
        assertInstanceOf(ASTLogicalExprNode.class, astLogicalExprNode);
        assertEquals(ASTAtomicExprNode.AtomicType.IDENTIFIER, astLogicalExprNode.operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals("a", astLogicalExprNode.operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getIdentifier());
        assertEquals(ASTAtomicExprNode.AtomicType.IDENTIFIER, astLogicalExprNode.operands().getLast().operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals("b", astLogicalExprNode.operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getIdentifier());
        assertEquals(ASTAtomicExprNode.AtomicType.IDENTIFIER, astLogicalExprNode.operands().getLast().operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals("c", astLogicalExprNode.operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getIdentifier());
        assertEquals(ASTLogicalExprNode.LogicalOperator.AND, astLogicalExprNode.operatorList.getFirst());
    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong operand type")
    void logicalExprIntegrationTestWrongType() {
        String code = """
                    9&&false
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTLogicalExprNode astLogicalExprNode = parser.parseLogicalExpression();
        symbolTableBuilder.visitLogicalExpr(astLogicalExprNode);

        assertNotNull(astLogicalExprNode);
        assertInstanceOf(ASTLogicalExprNode.class, astLogicalExprNode);
        assertEquals(ASTLogicalExprNode.LogicalOperator.AND, astLogicalExprNode.operatorList.getFirst());

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitLogicalExpr(astLogicalExprNode));
        assertTrue(exception.getMessage().contains("Type mismatch in logical expression"));

    }
}
