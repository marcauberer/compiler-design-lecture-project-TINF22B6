package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTTypeNode;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TypeNodeTest {
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
  @DisplayName("Test type")
  void testType() {
    // Arrange
    doReturn(new Token(TokenType.TOK_TYPE_EMPTY, "", new CodeLoc(3, 14))).when(lexer).getToken();
    doNothing().when(lexer).expect(TokenType.TOK_TYPE_EMPTY);

    // Execute parse method
    ASTTypeNode printBuiltinCallNode = parser.parseType();

    // Assert
    verify(lexer, times(4)).getToken();
    verify(lexer, times(1)).expect(TokenType.TOK_TYPE_EMPTY);
    assertNotNull(printBuiltinCallNode);
    assertInstanceOf(ASTTypeNode.class, printBuiltinCallNode);
    assertEquals(ASTTypeNode.DataType.EMPTY, printBuiltinCallNode.getType());
  }

  @Test
  @DisplayName("Integration test")
  void testTypeIntegrated() {
    String code = "int";

    // Create a Reader and Lexer
    Reader reader = new Reader(code);
    Lexer lexer = new Lexer(reader);

    // Create a Parser
    Parser parser = new Parser(lexer);

    // Parse the code
    ASTTypeNode typeNode = parser.parseType();

    // Assert the result
    assertEquals(ASTTypeNode.DataType.INT, typeNode.getType());
  }
}
