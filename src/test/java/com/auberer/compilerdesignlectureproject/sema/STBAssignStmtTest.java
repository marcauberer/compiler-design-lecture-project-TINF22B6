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
        assertNotNull(astAssignStmtNode);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        // Fake the symbol table entry for i
        Scope scope = symbolTableBuilder.getCurrentScopes().peek();
        symbolTableBuilder.currentScopes.peek().insertSymbol("i", astAssignStmtNode);

        symbolTableBuilder.visitAssignStmt(astAssignStmtNode);

        // Set type for i
        SymbolTableEntry entry = scope.lookupSymbolStrict("i", astAssignStmtNode);
        entry.updateType(new Type(SuperType.TY_INT));

        ExprResult result = typeChecker.visitAssignStmt(astAssignStmtNode);
        assertTrue(result.getType().is(SuperType.TY_INVALID));
    }

    @Test
    @DisplayName("Integration test - SymbolTableBuilder (Wrong Input)")
    void testAssignStmtIntegratedTypeCheckerWrongInput() {
        String code = "i = \"hallo\";";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTAssignStmtNode astAssignStmtNode = parser.parseAssignStmt();
        ASTLogicalExprNode astLogicalExprNode = astAssignStmtNode.getLogicalExpr();

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

        // Fake the symbol table entry for i
        symbolTableBuilder.currentScopes.peek().insertSymbol("i", astAssignStmtNode);
        SymbolTableEntry entry = symbolTableBuilder.currentScopes.peek().lookupSymbolStrict("i", astAssignStmtNode);
        entry.updateType(new Type(SuperType.TY_INT));

        symbolTableBuilder.visitAssignStmt(astAssignStmtNode);

        when(typeChecker.visitLogicalExpr(astLogicalExprNode)).thenReturn(new ExprResult(new Type(SuperType.TY_STRING)));

        Exception exception = assertThrows(SemaError.class, () -> typeChecker.visitAssignStmt(astAssignStmtNode));
        assertTrue(exception.getMessage().contains("L1C1: AssignStmt expects 'TY_INT,' but got 'TY_STRING'"));
    }
}