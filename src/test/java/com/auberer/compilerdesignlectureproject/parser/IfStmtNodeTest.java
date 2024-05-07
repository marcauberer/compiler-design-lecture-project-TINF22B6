package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTIFStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class IfStmtNodeTest {
  @Spy
  private Parser parser;

  @Mock
  private Lexer lexer;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    parser = new Parser(lexer);
    parser = spy(parser);
  }

  @Test
  @DisplayName("Test If Stmt")
  void testIfStmt() {
    // Arrange
    doNothing().when(lexer).advance();
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doNothing().when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);

    // Execute parse method
    ASTIFStmtNode ifStmtNode = parser.parseIfStmt();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_IF);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIFStmtNode.class, ifStmtNode);
  }

  @Test
  @DisplayName("Test IfElseStmt")
  void testIfElseStmt() {
    // Arrange
    doNothing().when(lexer).advance();
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doNothing().when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
    doNothing().when(parser).parseAfterIf();
    doNothing().when(parser).parseEPre();
    doNothing().when(lexer).expect(TokenType.TOK_ELSE);
    doNothing().when(parser).parseEPost();
    doNothing().when(parser).parseElseStmt();
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);

    // Execute parse method
    ASTIFStmtNode ifStmtNode = parser.parseIfStmt();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_IF);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
    verify(parser, times(1)).parseAfterIf();
    verify(parser, times(1)).parseEPre();
    verify(lexer, times(1)).expect(TokenType.TOK_ELSE);
    verify(parser, times(1)).parseEPost();
    verify(parser, times(1)).parseElseStmt();
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);

    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIFStmtNode.class, ifStmtNode);
  }

  @Test
  @DisplayName("Test IfElseIfStmt")
  void testIfElseIfStmt() {
    // Arrange
    doNothing().when(lexer).advance();
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doNothing().when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
    doNothing().when(parser).parseAfterIf();
    doNothing().when(parser).parseEPre();
    doNothing().when(lexer).expect(TokenType.TOK_ELSE);
    doNothing().when(parser).parseEPost();
    doNothing().when(parser).parseIfStmt();
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doNothing().when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);

    // Execute parse method
    ASTIFStmtNode ifStmtNode = parser.parseIfStmt();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_IF);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
    verify(parser, times(1)).parseAfterIf();
    verify(parser, times(1)).parseEPre();
    verify(lexer, times(1)).expect(TokenType.TOK_ELSE);
    verify(parser, times(1)).parseEPost();
    verify(parser, times(1)).parseIfStmt();
    verify(lexer, times(1)).expect(TokenType.TOK_IF);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);


    verify(parser, times(1)).parseEPost();
    verify(lexer, times(1)).expect(TokenType.TOK_ELSE);
    verify(parser, times(1)).parseEPost();
    verify(parser, times(1)).parseElseStmt();
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);

    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIFStmtNode.class, ifStmtNode);
  }

}
