package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IdentifierStateMachine extends StateMachine {
    @Override
    public void init() {
        State startState = new State("start");
        State endState = new State("end");
        startState.setStartState(true);
        endState.setAcceptState(true);
        addState(startState);
        addState(endState);

        Range upperCase = new Range('A', 'Z');
        Range lowerCase = new Range('a', 'z');
        Range digits = new Range('0', '9');

        addRangeTransition(startState, endState, upperCase);
        addRangeTransition(startState, endState, lowerCase);
        addCharTransition(startState, endState, '_');

        addRangeTransition(endState, endState, upperCase);
        addRangeTransition(endState, endState, lowerCase);
        addRangeTransition(endState, endState, digits);
        addCharTransition(endState, endState, '_');
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_IDENTIFIER;
        }

        return TokenType.TOK_INVALID;
    }
}
