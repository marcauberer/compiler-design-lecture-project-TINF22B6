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

        ASTStmtLstNode astStmtLstNode = parser.parseStmtLst();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        // Fake the symbol table entry for a,b,c
        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("a", astStmtLstNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("b", astStmtLstNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("c", astStmtLstNode);

        symbolTableBuilder.visitStmtLst(astStmtLstNode);

        // Set type for a,b,c
        SymbolTableEntry entryA = scope.lookupSymbolStrict("a", astStmtLstNode);
        entryA.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryB = scope.lookupSymbolStrict("b", astStmtLstNode);
        entryB.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryC = scope.lookupSymbolStrict("c", astStmtLstNode);
        entryC.updateType(new Type(SuperType.TY_BOOL));

        typeChecker.visitStmtLst(astStmtLstNode);
        assertNotNull(astStmtLstNode);
        assertInstanceOf(ASTStmtLstNode.class, astStmtLstNode);
        assertEquals(3, astStmtLstNode.getChildren(ASTStmtNode.class).size());
        ASTAssignStmtNode assignStmtNode = astStmtLstNode.getChild(ASTStmtNode.class, 2).getChild(ASTAssignStmtNode.class, 0);
        assertInstanceOf(ASTLogicalExprNode.class, assignStmtNode.getLogicalExpr());
        assertTrue(assignStmtNode.getLogicalExpr().getType().is(SuperType.TY_BOOL));
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
        assertEquals(ASTLogicalExprNode.LogicalOperator.AND, astLogicalExprNode.getOperatorList().getFirst());

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitLogicalExpr(astLogicalExprNode));
        assertTrue(exception.getMessage().contains("Type mismatch in logical expression"));

    }
}
