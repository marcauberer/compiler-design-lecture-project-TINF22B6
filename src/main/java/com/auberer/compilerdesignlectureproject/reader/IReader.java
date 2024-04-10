package com.auberer.compilerdesignlectureproject.reader;

public interface IReader {
  char getChar();
  CodeLoc getCodeLoc();
  void advance();
  void expect(char c) throws Exception;
  boolean isEOF();
}
