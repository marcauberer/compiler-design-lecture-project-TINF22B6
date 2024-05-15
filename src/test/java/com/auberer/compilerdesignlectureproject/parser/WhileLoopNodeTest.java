package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
import com.auberer.compilerdesignlectureproject.ast.ASTWhileLoopNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class WhileLoopNodeTest {

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
  @DisplayName("Test While Loop")
  void testWhileLoop() {
    doReturn(new Token(TokenType.TOK_WHILE, "", new CodeLoc(1, 1))).when(lexer).getToken();
    doNothing().when(lexer).expect(TokenType.TOK_WHILE);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doReturn(null).when(parser).parseLogicalExpression();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(null).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);

    // Execute parse method
    ASTWhileLoopNode astWhileLoopNode = parser.parseWhileLoop();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_WHILE);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
    verify(parser, times(1)).parseLogicalExpression();
    verify(parser, times(1)).parseStmtLst();

    assertNotNull(astWhileLoopNode);
    assertInstanceOf(ASTWhileLoopNode.class, astWhileLoopNode);
  }

  @Test
  @DisplayName("Integration test")
  void testWhileLoopIntegrated() {
    String code = "while (1) { int i = 5 + 6; double d = 12.3 + i; i = -13; }";
    Reader reader = new Reader(code);
    Lexer lexer1 = new Lexer(reader, false);
    Parser parser1 = new Parser(lexer1);
    ASTWhileLoopNode astWhileLoopNode = parser1.parseWhileLoop();

    assertNotNull(astWhileLoopNode);
    assertInstanceOf(ASTWhileLoopNode.class, astWhileLoopNode);
    assertInstanceOf(ASTLogicalExprNode.class, astWhileLoopNode.getLogicalExpr());
    astWhileLoopNode.getStmtLst().forEach(s -> assertInstanceOf(ASTStmtLstNode.class, s));
  }
}
