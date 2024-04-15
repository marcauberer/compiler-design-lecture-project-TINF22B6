package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

public class IntegerLiteralStateMachine extends StateMachine {

    @Override
    public void init() {
        State startState = new State("START");
        addState(startState);
        startState.setStartState(true);

        State integerState = new State("INTEGER");
        addState(integerState);
        integerState.setAcceptState(true);

        Range digitRange = new Range('0', '9');

        addRangeTransition(startState, integerState, digitRange);
        addRangeTransition(integerState, integerState, digitRange);
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_INTEGER;
        } else {
            return TokenType.TOK_INVALID;
        }
    }
}
