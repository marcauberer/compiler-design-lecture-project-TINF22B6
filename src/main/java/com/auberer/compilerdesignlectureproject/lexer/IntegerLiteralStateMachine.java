package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

/**
 * Matches an integer literal or throws an exception if the input could not be matched.
 */
public class IntegerLiteralStateMachine extends StateMachine {
  @Override
  public void init() {
    // Start state
    State stateStart = new State("Start");
    stateStart.setStartState(true);
    addState(stateStart);
    // Integer State
    State stateInt = new State("Integer");
    stateInt.setAcceptState(true);
    addState(stateInt);
    // Zero State
    State stateZero = new State("Zero");
    stateZero.setAcceptState(true);
    addState(stateZero);

    // Transitions
    Range digitsWithoutZero = new Range('1', '9');
    Range digits = new Range('0', '9');
    addRangeTransition(stateStart, stateInt, digitsWithoutZero);
    addRangeTransition(stateInt, stateInt, digits);
    addCharTransition(stateStart, stateZero, '0');
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.TOK_INT_LIT;
  }
}
