package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class StringLiteralStateMachine extends StateMachine {

    /*
     * Von Benjamin
     */
    @Override
    public void init() {
        State startState = new State("start State");
        State contentState = new State("content");
        State acceptState = new State("accept State");
        State failState = new State("fail State");

        startState.setStartState(true);
        acceptState.setAcceptState(true);
        Range großbuchstaben = new Range('A', 'Z');
        Range kleinbuchstaben = new Range('a', 'z');

        addState(startState);
        addState(contentState);
        addState(acceptState);
        addState(failState);

        addCharTransition(startState, contentState, '"');
        addElseTransition(startState, failState);

        addRangeTransition(contentState, contentState, großbuchstaben);
        addRangeTransition(contentState, contentState, kleinbuchstaben);
        addCharTransition(contentState, contentState, ' ');

        addCharTransition(contentState, acceptState, '"');

        addCharTransition(acceptState, acceptState, ' ');

        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.STRING;
    }
}
