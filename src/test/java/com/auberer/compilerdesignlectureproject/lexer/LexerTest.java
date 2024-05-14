package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LexerTest {

  @Test
  @DisplayName("Full lexer test")
  public void testAll() {
    String input = "if else while while\n3.21 123 1234 \"string\"identifier";
    Reader reader = new Reader(input);
    Lexer lexer = new Lexer(reader, false);
    assert !lexer.isEOF();
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_IF));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_ELSE));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_WHILE));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_WHILE));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_DOUBLE_LIT));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_INT_LIT));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_INT_LIT));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_STRING_LIT));
    assertDoesNotThrow(() -> lexer.expect(TokenType.TOK_IDENTIFIER));
    assert lexer.isEOF();
  }

}
