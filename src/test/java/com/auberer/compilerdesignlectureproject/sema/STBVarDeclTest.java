package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTVarDeclNode;
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

public class STBVarDeclTest {
    @Spy
    private TypeChecker typeChecker;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        typeChecker = new TypeChecker();
        typeChecker = spy(typeChecker);
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder (Correct Input)")
    void testVarDeclIntegratedTypeCheckerCorrectInput() {
        String code = "int i = 5;";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTVarDeclNode astVarDeclNode = parser.parseVarDecl();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitVarDecl(astVarDeclNode);

        when(typeChecker.visit(any())).thenReturn(new ExprResult(new Type(SuperType.TY_INT)));

        ExprResult result = typeChecker.visitVarDecl(astVarDeclNode);

        assertNotNull(astVarDeclNode);
        assertTrue(result.getType().is(SuperType.TY_INT));
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder (Wrong Input)")
    void testVarDeclIntegratedTypeCheckerWrongInput() {
        String code = "int i =  ";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTVarDeclNode astVarDeclNode = parser.parseVarDecl();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitVarDecl(astVarDeclNode);

        doReturn(new ExprResult(new Type(SuperType.TY_STRING))).when(typeChecker).visit(any());

        Exception exception = assertThrows(SemaError.class, () -> typeChecker.visitVarDecl(astVarDeclNode));
        assertTrue(exception.getMessage().contains("Variable Declaration - Type mismatch: cannot assign type 'TY_STRING'"));
    }
}