package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTPrintBuiltinCallNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrintBuiltinCallNodeTest {

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
  @DisplayName("Test print builtin call")
  void testPrintBuiltinCall() {
    // Arrange
    doNothing().when(lexer).advance();
    doNothing().when(lexer).expect(TokenType.TOK_PRINT);
    doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
    doNothing().when(parser).parseAssignExpr();
    doNothing().when(lexer).expect(TokenType.TOK_RPAREN);

    // Execute parse method
    ASTPrintBuiltinCallNode printBuiltinCallNode = parser.parsePrintBuiltinCall();

    // Assert
    verify(lexer, times(1)).expect(TokenType.TOK_PRINT);
    verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
    verify(parser, times(1)).parseAssignExpr();
    verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);
    assertNotNull(printBuiltinCallNode);
    assertInstanceOf(ASTPrintBuiltinCallNode.class, printBuiltinCallNode);
  }

  @Test
  @DisplayName("Integration test")
  void testPrintBuiltinCallIntegrated() {
    String code = "print();";

    // Create a Reader and Lexer
    Reader reader = new Reader(code);
    Lexer lexer = new Lexer(reader);

    // Create a Parser
    Parser parser = new Parser(lexer);

    // Parse the code
    ASTPrintBuiltinCallNode printBuiltinCallNode = parser.parsePrintBuiltinCall();

    // Assert the result
    assertNotNull(printBuiltinCallNode);
    assertInstanceOf(ASTPrintBuiltinCallNode.class, printBuiltinCallNode);
  }
}
