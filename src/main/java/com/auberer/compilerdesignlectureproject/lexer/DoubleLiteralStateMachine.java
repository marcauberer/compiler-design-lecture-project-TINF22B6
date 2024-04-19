package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

/**
 * Matches a double literal or throws an exception if the input could not be matched.
 */
public class DoubleLiteralStateMachine extends StateMachine {
  @Override
  public void init() {
    // Start state
    State stateStart = new State("Start");
    stateStart.setStartState(true);
    addState(stateStart);
    // State before dot
    State stateBeforeDot = new State("BeforeDot");
    addState(stateBeforeDot);
    // State dot
    State stateDot = new State("Dot");
    addState(stateDot);
    // State after dot
    State stateEnd = new State("Double");
    stateEnd.setAcceptState(true);
    addState(stateEnd);

    // Transitions
    Range digits = new Range('0', '9');
    addRangeTransition(stateStart, stateBeforeDot, digits);
    addCharTransition(stateStart, stateDot, '.');
    addRangeTransition(stateBeforeDot, stateBeforeDot, digits);
    addCharTransition(stateBeforeDot, stateDot, '.');
    addRangeTransition(stateDot, stateEnd, digits);
    addRangeTransition(stateEnd, stateEnd, digits);
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.TOK_DOUBLE_LIT;
  }
}
