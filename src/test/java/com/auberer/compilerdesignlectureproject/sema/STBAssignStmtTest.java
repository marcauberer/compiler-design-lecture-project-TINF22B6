package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTAssignStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class STBAssignStmtTest {
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
    void testAssignStmtIntegratedTypeCheckerCorrectInput() {
        String code = "i = 5;";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTAssignStmtNode astAssignStmtNode = parser.parseAssignStmt();
        ASTLogicalExprNode astLogicalExprNode = astAssignStmtNode.getLogicalExpr();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitAssignStmt(astAssignStmtNode);

        when(typeChecker.visitLogicalExpr(astLogicalExprNode)).thenReturn(new ExprResult(new Type(SuperType.TY_STRING)));

        ExprResult result = typeChecker.visitAssignStmt(astAssignStmtNode);

        assertNotNull(astAssignStmtNode);
        assertTrue(result.getType().is(SuperType.TY_INT));
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder (Wrong Input)")
    void testAssignStmtIntegratedTypeCheckerWrongInput() {
        String code = "i =  ";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTAssignStmtNode astAssignStmtNode = parser.parseAssignStmt();
        ASTLogicalExprNode astLogicalExprNode = astAssignStmtNode.getLogicalExpr();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visitAssignStmt(astAssignStmtNode);

        when(typeChecker.visitLogicalExpr(astLogicalExprNode)).thenReturn(new ExprResult(new Type(SuperType.TY_STRING)));

        Exception exception = assertThrows(SemaError.class, () -> typeChecker.visitAssignStmt(astAssignStmtNode));
        assertTrue(exception.getMessage().contains("Assignment - Type mismatch: cannot assign type 'TY_STRING'"));
    }
}