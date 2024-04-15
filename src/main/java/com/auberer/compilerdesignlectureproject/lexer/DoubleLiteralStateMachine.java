package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

public class DoubleLiteralStateMachine extends StateMachine {

    @Override
    public void init() {
        State startState = new State("START");
        addState(startState);
        startState.setStartState(true);

        State integerState = new State("INTEGER");
        addState(integerState);

        State decimalPointState = new State("DECIMAL_POINT");
        addState(decimalPointState);

        State decimalState = new State("DECIMAL");
        addState(decimalState);
        decimalState.setAcceptState(true);

        Range digitRange = new Range('0', '9');

        addRangeTransition(startState, integerState, digitRange);
        addCharTransition(startState, decimalPointState, '.');
        addRangeTransition(integerState, integerState, digitRange);
        addCharTransition(integerState, decimalPointState, '.');
        addRangeTransition(decimalPointState, decimalState, digitRange);
        addRangeTransition(decimalState, decimalState, digitRange);
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_DOUBLE;
        } else {
            return TokenType.TOK_INVALID;
        }

    }
}
