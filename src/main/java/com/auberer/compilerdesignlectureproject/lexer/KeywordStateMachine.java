package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

/**
 * Matches the given keyword or throws an exception if the input could not be matched.
 */
public class KeywordStateMachine extends StateMachine {
  private final String keyword;
  private final TokenType tokenType;

  public KeywordStateMachine(String keyword, TokenType tokenType) {
    // We only want to allow lowercase keywords with length > 0
    assert keyword.matches("[a-z]+");
    this.keyword = keyword;
    this.tokenType = tokenType;
  }

  @Override
  public void init() {
    // Start state
    State stateStart = new State("Start");
    stateStart.setStartState(true);
    addState(stateStart);

    // Letter states
    State[] letterStates = new State[keyword.length()];
    for (int i = 0; i < keyword.length(); i++) {
      letterStates[i] = new State("Letter " + i);
      addState(letterStates[i]);
      addCharTransition(i == 0 ? stateStart : letterStates[i - 1], letterStates[i], keyword.charAt(i));
    }

    // Set the last state as the accept state
    letterStates[keyword.length() - 1].setAcceptState(true);
  }

  @Override
  public TokenType getTokenType() {
    return tokenType;
  }
}
