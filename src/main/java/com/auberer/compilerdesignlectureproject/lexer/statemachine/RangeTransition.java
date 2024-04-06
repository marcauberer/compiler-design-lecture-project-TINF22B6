package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import lombok.Getter;

@Getter
public class RangeTransition extends Transition {
  private final Range range;

  public RangeTransition(State fromState, State toState, Range range) {
    super(fromState, toState);
    this.range = range;
  }

  @Override
  public boolean matches(char input) {
    return range.contains(input);
  }

  @Override
  public String getDotCode() {
    return fromState.getName() + " -> " + toState.getName() + " [label=\"" + range.toString() + "\"]";
  }
}
