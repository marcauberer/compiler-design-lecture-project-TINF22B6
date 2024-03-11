package com.auberer.compilerdesignlectureproject.reader;

public interface IReader {
  char getChar();
  CodeLoc getCodeLoc();
  void advance();
  void expect();
  boolean isEOF();
}
