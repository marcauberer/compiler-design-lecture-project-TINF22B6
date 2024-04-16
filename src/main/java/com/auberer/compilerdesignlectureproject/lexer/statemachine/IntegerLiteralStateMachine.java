package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IntegerLiteralStateMachine extends StateMachine {
    @Override
    public void init() {
        State startState = new State("start");
        State endState = new State("end");
        startState.setStartState(true);
        endState.setAcceptState(true);
        addState(startState);
        addState(endState);

        Range digits = new Range('0', '9');
        addRangeTransition(startState, endState, digits);
        addRangeTransition(endState, endState, digits);
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_INTEGER_LITERAL;
        }

        return TokenType.TOK_INVALID;
    }
}