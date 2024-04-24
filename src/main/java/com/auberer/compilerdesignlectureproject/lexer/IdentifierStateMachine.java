package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

/**
 * Matches an identifier or throws an exception if the input could not be matched.
 */
public class IdentifierStateMachine extends StateMachine {
  @Override
  public void init() {
    // Start state
    State stateStart = new State("Start");
    stateStart.setStartState(true);
    addState(stateStart);
    // Identifier state
    State stateEnd = new State("Identifier");
    stateEnd.setAcceptState(true);
    addState(stateEnd);

    // Transitions
    Range letters = new Range('a', 'z');
    Range capitalLetters = new Range('A', 'Z');
    Range digits = new Range('0', '9');
    addRangeTransition(stateStart, stateEnd, letters);
    addRangeTransition(stateStart, stateEnd, capitalLetters);
    addCharTransition(stateEnd, stateEnd, '_');
    addRangeTransition(stateEnd, stateEnd, letters);
    addRangeTransition(stateEnd, stateEnd, capitalLetters);
    addRangeTransition(stateEnd, stateEnd, digits);
    addCharTransition(stateEnd, stateEnd, '_');
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.TOK_IDENTIFIER;
  }
}
