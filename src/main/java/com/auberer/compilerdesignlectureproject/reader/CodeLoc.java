package com.auberer.compilerdesignlectureproject.reader;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CodeLoc implements Cloneable, Comparable {
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

  @Override
  public int compareTo(Object o) {
    if (line == ((CodeLoc) o).line && column == ((CodeLoc) o).column)
      return 0;
    else if (line == ((CodeLoc) o).line)
      return column < ((CodeLoc) o).column ? -1 : 1;
    else
      return line < ((CodeLoc) o).line ? -1 : 1;
  }
}
