package com.auberer.compilerdesignlectureproject.reader;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CodeLoc implements Cloneable {
  long line;
  long column;

  public String toString() {
    return "L" + line + "C" + column;
  }

  @Override
  public CodeLoc clone() {
    try {
      CodeLoc clone = (CodeLoc) super.clone();
      clone.line = line;
      clone.column = column;
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
