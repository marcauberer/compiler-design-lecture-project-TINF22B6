package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Transition {
  protected State fromState;
  protected State toState;

  public abstract boolean matches(char input);
  public abstract String getDotCode();
}
