package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class KeywordStateMachine extends StateMachine {

    private final String keyword;

    public KeywordStateMachine(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void init() {
        State[] states = new State[keyword.length() + 1];

        for (int i = 0; i < states.length; i++) {
            states[i] = new State("q" + i);
            addState(states[i]);
        }

        states[0].setStartState(true);
        states[states.length-1].setAcceptState(true);

        for (int i = 0; i < keyword.length(); i++) {
            addCharTransition(states[i], states[i+1], keyword.charAt(i));
        }
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_KEYWORD;
        }

        return TokenType.TOK_INVALID;
    }
}