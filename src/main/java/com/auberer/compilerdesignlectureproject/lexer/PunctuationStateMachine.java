package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

/**
 * Matches the given keyword or throws an exception if the input could not be matched.
 */
public class PunctuationStateMachine extends StateMachine {
  private final String punctuation;
  private final TokenType tokenType;

  public PunctuationStateMachine(String punctuation, TokenType tokenType) {
    this.punctuation = punctuation;
    this.tokenType = tokenType;
  }

  @Override
  public void init() {
    // Start state
    State stateStart = new State("Start");
    stateStart.setStartState(true);
    addState(stateStart);

    // Letter states
    State[] letterStates = new State[punctuation.length()];
    for (int i = 0; i < punctuation.length(); i++) {
      letterStates[i] = new State("Letter " + i);
      addState(letterStates[i]);
      addCharTransition(i == 0 ? stateStart : letterStates[i - 1], letterStates[i], punctuation.charAt(i));
    }

    // Set the last state as the accept state
    letterStates[punctuation.length() - 1].setAcceptState(true);
  }

  @Override
  public TokenType getTokenType() {
    return tokenType;
  }
}
