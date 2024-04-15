package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;

public class KeywordStateMachine extends StateMachine {

    private final String javaKeywords;
    boolean isKeyword;

    public KeywordStateMachine(String javaKeywords) {
        this.javaKeywords = javaKeywords;
    }

    @Override
    public void init() {
        State[] states = new State[javaKeywords.length() + 1];

        for (int i = 0; i < states.length; i++) {
            states[i] = new State("q" + i);
            addState(states[i]);
        }

        states[0].setStartState(true);
        states[states.length-1].setAcceptState(true);

        for (int i = 0; i < javaKeywords.length(); i++) {
            addCharTransition(states[i], states[i+1], javaKeywords.charAt(i));
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
