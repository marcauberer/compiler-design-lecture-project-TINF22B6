package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class DoubleLiteralStateMachine extends StateMachine {
    @Override
    public void init() {
        State start = new State("start");
        State intermediate = new State("intermediate");
        State end = new State("end");
        start.setStartState(true);
        end.setAcceptState(true);
        addState(start);
        addState(end);

        Range digits = new Range('0', '9');
        addRangeTransition(start, start, digits);
        addCharTransition(start, intermediate, '.');
        addRangeTransition(intermediate, end, digits);
        addRangeTransition(end, end, digits);
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_DOUBLE_LITERAL;
        }

        return TokenType.TOK_INVALID;
    }
}
