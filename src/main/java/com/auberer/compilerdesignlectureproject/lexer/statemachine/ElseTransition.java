package com.auberer.compilerdesignlectureproject.lexer.statemachine;

public class ElseTransition extends Transition {
  public static final char ELSE_TRANSITION_LABEL = '*';

  public ElseTransition(State fromState, State toState) {
    super(fromState, toState);
  }

  @Override
  public boolean matches(char input) {
    return true;
  }

  @Override
  public String getDotCode() {
    return fromState.getName() + " -> " + toState.getName() + " [label=\"" + ELSE_TRANSITION_LABEL + "\"]";
  }
}
