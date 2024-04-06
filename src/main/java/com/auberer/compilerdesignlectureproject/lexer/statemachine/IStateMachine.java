package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public interface IStateMachine {
  void init();
  void addState(State state);
  void addCharTransition(State from, State to, char input);
  void addRangeTransition(State from, State to, Range range);
  void addElseTransition(State from, State to);
  void processInput(char input) throws IllegalStateException;
  void reset();
  State getCurrentState();
  String getDotCode();
  void dumpDotCode();
  TokenType getTokenType();
}
