package com.auberer.compilerdesignlectureproject.reader;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CodeLoc {
  long line;
  long column;

  public String toString() {
    return "L" + line + "C" + column;
  }
}
