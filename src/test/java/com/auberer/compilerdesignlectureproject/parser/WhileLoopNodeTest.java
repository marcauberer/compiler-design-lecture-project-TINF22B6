package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTStmtLstNode;
import com.auberer.compilerdesignlectureproject.ast.ASTWhileLoopNode;
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
    doNothing().when(lexer).advance();
    doNothing().when(lexer).expect(TokenType.TOK_WHILE);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doNothing().when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
    doNothing().when(lexer).expect(TokenType.TOK_LBRACE);
    doReturn(new ASTStmtLstNode()).when(parser).parseStmtLst();
    doNothing().when(lexer).expect(TokenType.TOK_RBRACE);


    // Execute parse method
    ASTWhileLoopNode astWhileLoopNode = parser.parseWhileLoop();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_WHILE);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    verify(lexer, times(1)).expect(TokenType.TOK_LBRACE);
    verify(lexer, times(1)).expect(TokenType.TOK_RBRACE);
    verify(parser, times(1)).parseAssignExpr();
    verify(parser, times(1)).parseStmtLst();

    assertNotNull(astWhileLoopNode);
    assertInstanceOf(ASTWhileLoopNode.class, astWhileLoopNode);
  }

  @Test
  @DisplayName("Integration test")
  void testWhileLoopIntegrated() {
    // ToDo: Implement integration test to test reader, lexer and parser together
  }
}
