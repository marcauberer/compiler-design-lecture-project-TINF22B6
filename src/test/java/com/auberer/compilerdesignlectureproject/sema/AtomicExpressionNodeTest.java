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

        ASTAtomicExprNode astAtomicExprNode = parser.parseAtomicExpression();
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();


        TypeChecker typeChecker = new TypeChecker();

        // Fake the symbol table entry for a,b,c
        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("a", astAtomicExprNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("b", astAtomicExprNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("c", astAtomicExprNode);

        symbolTableBuilder.visitAtomicExpr(astAtomicExprNode);

        // Set type for a,b,c
        SymbolTableEntry entryA = scope.lookupSymbolStrict("a", astAtomicExprNode);
        entryA.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryB = scope.lookupSymbolStrict("b", astAtomicExprNode);
        entryB.updateType(new Type(SuperType.TY_BOOL));
        SymbolTableEntry entryC = scope.lookupSymbolStrict("c", astAtomicExprNode);
        entryC.updateType(new Type(SuperType.TY_BOOL));

        ExprResult result = typeChecker.visitAtomicExpr(astAtomicExprNode);
        assertTrue(result.getType().is(SuperType.TY_BOOL));
        assertNotNull(astAtomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, astAtomicExprNode);
        assertEquals(ASTAtomicExprNode.AtomicType.IDENTIFIER, astAtomicExprNode.getLogicalExpr().operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals("a", astAtomicExprNode.getLogicalExpr().operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getIdentifier());
        assertEquals(ASTAtomicExprNode.AtomicType.IDENTIFIER, astAtomicExprNode.getLogicalExpr().operands().getLast().operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals("b", astAtomicExprNode.getLogicalExpr().operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getIdentifier());
        assertEquals(ASTAtomicExprNode.AtomicType.IDENTIFIER, astAtomicExprNode.getLogicalExpr().operands().getLast().operands().getFirst().operands().getFirst().operands().getFirst().operand().getExprType());
        assertEquals("c", astAtomicExprNode.getLogicalExpr().operands().getFirst().operands().getFirst().operands().getFirst().operands().getFirst().operand().getIdentifier());
        assertEquals(ASTLogicalExprNode.LogicalOperator.AND, astAtomicExprNode.getLogicalExpr().operatorList.get(0));
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
                    counter.getNumber()
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        ASTAtomicExprNode astAtomicExprNode = parser.parseAtomicExpression();

        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("counter", astAtomicExprNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("getNumber()", astAtomicExprNode);

        symbolTableBuilder.visitAtomicExpr(astAtomicExprNode);

        SymbolTableEntry entryIdentifier = scope.lookupSymbolStrict("counter", astAtomicExprNode);
        entryIdentifier.updateType(new Type(SuperType.TY_INVALID));
        SymbolTableEntry entryFunction = scope.lookupSymbolStrict("getNumber()", astAtomicExprNode);
        entryFunction.updateType(new Type(SuperType.TY_FUNCTION));

        assertNotNull(astAtomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, astAtomicExprNode);
        assertInstanceOf(ASTFctCallNode.class, astAtomicExprNode.getFctCall());
    }

    @Test
    public void checkPrintBuiltInCallResult() {
        // Create a new Reader object with the given file path
        String code = """
                    System.Println("Hello World");
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        ASTAtomicExprNode astAtomicExprNode = parser.parseAtomicExpression();

        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("System", astAtomicExprNode);
        symbolTableBuilder.currentScopes.peek().insertSymbol("Println()", astAtomicExprNode);

        symbolTableBuilder.visitAtomicExpr(astAtomicExprNode);

        SymbolTableEntry entryIdentifier = scope.lookupSymbolStrict("System", astAtomicExprNode);
        entryIdentifier.updateType(new Type(SuperType.TY_INVALID));
        SymbolTableEntry entryFunction = scope.lookupSymbolStrict("Println()", astAtomicExprNode);
        entryFunction.updateType(new Type(SuperType.TY_FUNCTION));

        assertNotNull(astAtomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, astAtomicExprNode);
        assertInstanceOf(ASTPrintBuiltinCallNode.class , astAtomicExprNode.getPrintCall());
    }

}
