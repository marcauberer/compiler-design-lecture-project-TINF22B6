package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;
import java.util.List;

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
    List<Token> tokenList = new LinkedList<>();
    tokenList.add(new Token(TokenType.TOK_IF, "", new CodeLoc(1, 1)));
    tokenList.add(null);

    // Arrange
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doReturn(null).when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
    doReturn(tokenList.get(0), tokenList.get(0), tokenList.get(1)).when(lexer).getToken();

    // Execute parse method
    ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_IF);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(1)).parseStmtLst();
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIfStmtNode.class, ifStmtNode);
  }

  @Test
  @DisplayName("Test If-Else Stmt")
  void testIfElseStmt() {
    List<Token> tokenList = new LinkedList<>();
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 1)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 2)));

    // Arrange
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doReturn(null).when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
    doNothing().when(lexer).expect(TokenType.TOK_ELSE);
    doReturn(tokenList.get(0), tokenList.get(1)).when(lexer).getToken();

    // Execute parse method
    ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_IF);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(2)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(2)).parseStmtLst();
    verify(lexer, times(2)).expect(TokenType.TOK_RBRACE);
    verify(lexer, times(1)).expect(TokenType.TOK_ELSE);

    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIfStmtNode.class, ifStmtNode);
  }

  @Test
  @DisplayName("Test If-ElseIf-Else Stmt")
  void testIfElseIfStmt() {
    List<Token> tokenList = new LinkedList<>();
    tokenList.add(new Token(TokenType.TOK_IF, "", new CodeLoc(1, 1)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 2)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 2)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 2)));
    tokenList.add(new Token(TokenType.TOK_IF, "", new CodeLoc(1, 3)));
    tokenList.add(new Token(TokenType.TOK_IF, "", new CodeLoc(1, 3)));
    tokenList.add(new Token(TokenType.TOK_IF, "", new CodeLoc(1, 3)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 4)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 4)));
    tokenList.add(new Token(TokenType.TOK_ELSE, "", new CodeLoc(1, 4)));
    tokenList.add(new Token(TokenType.TOK_LBRACE, "", new CodeLoc(1, 5)));
    tokenList.add(new Token(TokenType.TOK_LBRACE, "", new CodeLoc(1, 5)));

    // Arrange
    doNothing().when(lexer).expect(TokenType.TOK_IF);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doReturn(null).when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);
    doNothing().when(lexer).expect(TokenType.TOK_ELSE);
    doReturn(
        tokenList.get(0),
        tokenList.get(1),
        tokenList.get(2),
        tokenList.get(3),
        tokenList.get(4),
        tokenList.get(5),
        tokenList.get(6),
        tokenList.get(7),
        tokenList.get(8),
        tokenList.get(9),
        tokenList.get(10),
        tokenList.get(11)
    ).when(lexer).getToken();

    // Execute parse method
    ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

    // Assert
    verify(lexer, times(2)).expect(TokenType.TOK_IF);
    verify(lexer, times(2)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(2)).parseAssignExpr();
    verify(lexer, times(2)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(3)).expect(TokenType.TOK_LBRACE);
    verify(parser, times(3)).parseStmtLst();
    verify(lexer, times(3)).expect(TokenType.TOK_RBRACE);
    verify(lexer, times(2)).expect(TokenType.TOK_ELSE);

    assertNotNull(ifStmtNode);
    assertInstanceOf(ASTIfStmtNode.class, ifStmtNode);
  }

}
