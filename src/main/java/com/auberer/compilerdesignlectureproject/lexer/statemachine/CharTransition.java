package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import lombok.Getter;

@Getter
public class CharTransition extends Transition {
  private final char input;

  public CharTransition(State fromState, State toState, char input) {
    super(fromState, toState);
    this.input = input;
  }

  @Override
  public boolean matches(char input) {
    return this.input == input;
  }

  @Override
  public String getDotCode() {
    return fromState.getName() + " -> " + toState.getName() + " [label=\"" + input + "\"]";
  }
}
