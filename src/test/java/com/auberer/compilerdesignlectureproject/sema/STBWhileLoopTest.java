package com.auberer.compilerdesignlectureproject.sema;


import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTWhileLoopNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class STBWhileLoopTest {
    @Spy
    private TypeChecker typeChecker;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        typeChecker = new TypeChecker();
        typeChecker = spy(typeChecker);
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder")
    void testWhileLoopIntegratedSymbolTableBuilder() {
        String code = "while (1) { int i = 5 + 6; double d = 12.3 + i; i = -13; }";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTWhileLoopNode astWhileLoopNode = parser.parseWhileLoop();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitWhileLoop(astWhileLoopNode);

        assertNotNull(astWhileLoopNode);
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder (Correct Input)")
    void testWhileLoopIntegratedTypeCheckerCorrectInput() {
        String code = "while (i == 1) { int i = 5 + 6; double d = 12.3 + i; i = -13; }";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTWhileLoopNode astWhileLoopNode = parser.parseWhileLoop();
        ASTLogicalExprNode astLogicalExprNode = astWhileLoopNode.getLogicalExpr();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitWhileLoop(astWhileLoopNode);

        when(typeChecker.visitLogicalExpr(astLogicalExprNode)).thenReturn(new ExprResult(new Type(SuperType.TY_BOOL)));

        ExprResult result = typeChecker.visitWhileLoop(astWhileLoopNode);

        assertNotNull(astWhileLoopNode);
        assertTrue(result.getType().isOneOf(SuperType.TY_EMPTY));
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder (Wrong Input)")
    void testWhileLoopIntegratedTypeCheckerWrongInput() {
        String code = "while ( sdsd ) { int i = 5 + 6; double d = 12.3 + i; i = -13; }";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTWhileLoopNode astWhileLoopNode = parser.parseWhileLoop();
        ASTLogicalExprNode astLogicalExprNode = astWhileLoopNode.getLogicalExpr();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitWhileLoop(astWhileLoopNode);

        doReturn(new ExprResult(new Type(SuperType.TY_STRING))).when(typeChecker).visitLogicalExpr(astLogicalExprNode);

        Exception exception = assertThrows(SemaError.class, () -> typeChecker.visitWhileLoop(astWhileLoopNode));
        assertTrue(exception.getMessage().contains("While Loop - Condition expects boolean but got 'TY_STRING'"));
    }
}