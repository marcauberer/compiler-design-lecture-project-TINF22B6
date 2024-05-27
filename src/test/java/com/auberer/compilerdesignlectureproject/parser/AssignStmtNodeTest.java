package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTAssignStmtNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVarDeclNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignStmtNodeTest {
    @Spy
    private Parser parser; // Use spy to allow partial mocking

    @Mock
    private Lexer lexer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = new Parser(lexer);
        parser = spy(parser);
    }

    @Test
    @DisplayName("Test assign statement without optional")
    void testAssignStmtWithoutOptional() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_INVALID, "", new CodeLoc(1, 1)));

        doReturn(tokenList.getFirst()).when(lexer).getToken();
        doReturn(null).when(parser).parseLogicalExpression();

        // Execute parse method
        ASTAssignStmtNode assignStmt = parser.parseAssignStmt();

        // Assert
        verify(lexer, times(2)).getToken();
        verify(parser, times(1)).parseLogicalExpression();
        assertNotNull(assignStmt);
        assertInstanceOf(ASTAssignStmtNode.class, assignStmt);
        // Ensure the variable name is empty
        assertNull(assignStmt.getVariableName());
    }

    @Test
    @DisplayName("Test assign statement with optional")
    void testAssignStmtWithOptional() {
        List<Token> tokenList = new LinkedList<>();
        tokenList.add(new Token(TokenType.TOK_IDENTIFIER, "xyz", new CodeLoc(1, 1)));

        doReturn(tokenList.getFirst()).when(lexer).getToken();
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doNothing().when(lexer).expect(TokenType.TOK_ASSIGN);
        doReturn(null).when(parser).parseLogicalExpression();

        // Execute parse method
        ASTAssignStmtNode assignStmt = parser.parseAssignStmt();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        verify(lexer, times(1)).expect(TokenType.TOK_ASSIGN);
        verify(parser, times(1)).parseLogicalExpression();
        assertNotNull(assignStmt);
        assertInstanceOf(ASTAssignStmtNode.class, assignStmt);
        // Ensure the variable name is correct
        assertEquals("xyz", assignStmt.getVariableName());
    }

    @Test
    @DisplayName("Integration test")
    void integrationTestAssignStmtNode() {
        String code = "x = 5;";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTAssignStmtNode astStmtNode = parser.parseAssignStmt();


        assertNotNull(astStmtNode);
        assertInstanceOf(ASTAssignStmtNode.class, astStmtNode);
        assertNotNull(astStmtNode.getLogicalExpr());
        assertInstanceOf(ASTLogicalExprNode.class, astStmtNode.getLogicalExpr());
    }

    @Test
    @DisplayName("Integration test")
    void integrationTestSymbolTableBuilder() {
        String code = "x = 5;";
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTAssignStmtNode astStmtNode = parser.parseAssignStmt();

        SymbolTableBuilder symboltablebuilder = new SymbolTableBuilder();

        // Modify the symbol table, so that it contains the variable x
        ASTVarDeclNode declNode = new ASTVarDeclNode();
        declNode.setCodeLoc(new CodeLoc(0, 0));
        symboltablebuilder.getCurrentScopes().peek().insertSymbol("x", declNode);

        symboltablebuilder.visitAssignStmt(astStmtNode);
    }

}