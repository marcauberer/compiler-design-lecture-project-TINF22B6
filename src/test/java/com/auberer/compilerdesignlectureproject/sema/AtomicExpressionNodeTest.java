package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AtomicExpressionNodeTest {
    @Test
    public void checkIntResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
                        6
                        """
                        );

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTAtomicExprNode atomicExprNode = parser.parseAtomicExpression();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitAtomicExpr(atomicExprNode);

        assertEquals(SuperType.TY_INT.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }

    @Test
    public void checkDoubleResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
                        4.2
                        """
        );

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTAtomicExprNode atomicExprNode = parser.parseAtomicExpression();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitAtomicExpr(atomicExprNode);

        assertEquals(SuperType.TY_DOUBLE.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }

    @Test
    public void checkStringResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
        "XSLT"
        """);

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTAtomicExprNode atomicExprNode = parser.parseAtomicExpression();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitAtomicExpr(atomicExprNode);

        assertEquals(SuperType.TY_STRING.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }

    @Test
    public void checkBoolResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
        false
        """
        );

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTAtomicExprNode atomicExprNode = parser.parseAtomicExpression();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitAtomicExpr(atomicExprNode);

        assertEquals(SuperType.TY_BOOL.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }

    @Test
    public void checkLogicalExprResult() {
        // Create a new Reader object with the given file path
        String code = """
                        a = true;
                        b = true;
                     	c = a&&b;
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTStmtLstNode stmtLstNode = parser.parseStmtLst();
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        TypeChecker typeChecker = new TypeChecker();

        // Fake the symbol table entry for a,b,c
        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("a", stmtLstNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("b", stmtLstNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("c", stmtLstNode);

        symbolTableBuilder.visitStmtLst(stmtLstNode);

        // Set type for a,b,c
        SymbolTableEntry entryA = scope.lookupSymbolStrict("a", stmtLstNode);
        entryA.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryB = scope.lookupSymbolStrict("b", stmtLstNode);
        entryB.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryC = scope.lookupSymbolStrict("c", stmtLstNode);
        entryC.updateType(new Type(SuperType.TY_BOOL));

        typeChecker.visitStmtLst(stmtLstNode);
        assertNotNull(stmtLstNode);
        assertInstanceOf(ASTStmtLstNode.class, stmtLstNode);
        assertEquals(3, stmtLstNode.getChildren(ASTStmtNode.class).size());
        ASTAssignStmtNode assignStmtNode = stmtLstNode.getChildren(ASTStmtNode.class).get(2).getChild(ASTAssignStmtNode.class, 0);
        assertInstanceOf(ASTLogicalExprNode.class, assignStmtNode.getLogicalExpr());
        assertTrue(assignStmtNode.getLogicalExpr().getType().is(SuperType.TY_BOOL));
    }

    @Test
    public void checkIdentifierResult() {
        // Create a new Reader object with the given file path
        String code = """
                    a=0;
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        ASTAtomicExprNode astAtomicExprNode = parser.parseAtomicExpression();

        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("a", astAtomicExprNode);

        symbolTableBuilder.visitAtomicExpr(astAtomicExprNode);

        SymbolTableEntry entryA = scope.lookupSymbolStrict("a", astAtomicExprNode);
        entryA.updateType(new Type(SuperType.TY_INT));

        assertNotNull(astAtomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, astAtomicExprNode);
    }

    @Test
    public void checkFunctionCallResult() {
        // Create a new Reader object with the given file path
        String code = """
                    call getNumber()
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        ASTAtomicExprNode astAtomicExprNode = parser.parseAtomicExpression();

        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("getNumber", astAtomicExprNode);

        symbolTableBuilder.visitAtomicExpr(astAtomicExprNode);

        SymbolTableEntry entryFunction = scope.lookupSymbolStrict("getNumber", astAtomicExprNode);
        entryFunction.updateType(new Type(SuperType.TY_FUNCTION));

        assertNotNull(astAtomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, astAtomicExprNode);
        assertInstanceOf(ASTFctCallNode.class, astAtomicExprNode.getFctCall());
    }

    @Test
    public void checkPrintBuiltInCallResult() {
        // Create a new Reader object with the given file path
        String code = """
                    print("Hello World");
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        ASTAtomicExprNode astAtomicExprNode = parser.parseAtomicExpression();

        symbolTableBuilder.visitAtomicExpr(astAtomicExprNode);

        assertNotNull(astAtomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, astAtomicExprNode);
        assertInstanceOf(ASTPrintBuiltinCallNode.class, astAtomicExprNode.getPrintCall());
    }

}
