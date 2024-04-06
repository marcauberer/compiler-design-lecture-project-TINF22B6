package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import lombok.Data;

@Data
public class Range {
  private final char start;
  private final char end;

  public boolean contains(char c) {
    return c >= start && c <= end;
  }

  public boolean isSingleCharacter() {
    return start == end;
  }

  public boolean isValid() {
    return start < end;
  }

  public String toString() {
    return isSingleCharacter() ? "[" + start + "]" : "[" + start + "-" + end + "]";
  }
}
