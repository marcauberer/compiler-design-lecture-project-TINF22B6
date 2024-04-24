package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

/**
 * Matches a string literal or throws an exception if the input could not be matched.
 */
public class StringLiteralStateMachine extends StateMachine {
  @Override
  public void init() {
    // Start state
    State stateStart = new State("Start");
    stateStart.setStartState(true);
    addState(stateStart);
    // Content state
    State stateContent = new State("Content");
    addState(stateContent);
    // End state
    State stateEnd = new State("String");
    stateEnd.setAcceptState(true);
    addState(stateEnd);

    // Transitions
    addCharTransition(stateStart, stateContent, '"');
    addRangeTransition(stateContent, stateContent, new Range(' ', '!'));
    addRangeTransition(stateContent, stateContent, new Range('#', '~'));
    addCharTransition(stateContent, stateEnd, '"');
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.TOK_STRING_LIT;
  }
}
