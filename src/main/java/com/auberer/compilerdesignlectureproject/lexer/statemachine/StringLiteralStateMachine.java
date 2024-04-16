package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class StringLiteralStateMachine extends StateMachine {
    @Override
    public void init() {
        State startState = new State("start");
        State intermediate = new State("intermediate");
        State endState = new State("end");
        startState.setStartState(true);
        endState.setAcceptState(true);
        addState(startState);
        addState(intermediate);
        addState(endState);

        addCharTransition(startState, intermediate, '"');
        addRangeTransition(intermediate, intermediate, new Range(' ', '!')); // writable ASCII chars before '"'
        addRangeTransition(intermediate, intermediate, new Range('#', '~')); // writable ASCII chars after '"'
        addCharTransition(intermediate, endState, '"');
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_STRING_LITERAL;
        }

        return TokenType.TOK_INVALID;
    }
}
