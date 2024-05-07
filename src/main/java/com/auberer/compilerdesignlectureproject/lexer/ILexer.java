package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;

import java.util.Set;

public interface ILexer {
  Token getToken();
  void advance();
  void expect(TokenType expectedType);
  void expectOneOf(Set<TokenType> expectedTypes);
  boolean isEOF();
  CodeLoc getCodeLoc();
}
