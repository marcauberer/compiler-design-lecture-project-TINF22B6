package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;

public class KeywordStateMachine extends StateMachine {

    private final String keywords;

    public KeywordStateMachine(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public void init() {
        State[] states = new State[keywords.length() + 1];

        for (int i = 0; i < states.length; i++) {
            states[i] = new State("q" + i);
            addState(states[i]);
        }

        states[0].setStartState(true);
        states[states.length-1].setAcceptState(true);

        for (int i = 0; i < keywords.length(); i++) {
            addCharTransition(states[i], states[i+1], keywords.charAt(i));
        }

    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_KEYWORD;
        } else {
            return TokenType.TOK_IDENTIFIER;
        }
    }
}
