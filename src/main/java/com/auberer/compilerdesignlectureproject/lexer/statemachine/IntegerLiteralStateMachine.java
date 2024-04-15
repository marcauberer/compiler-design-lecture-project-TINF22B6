package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IntegerLiteralStateMachine extends StateMachine{
    @Override
    public void init() {
        State state0 = new State("State 0");
        State state1 = new State("State 1");
        State state2 = new State("State 2");
        State state3 = new State("State 3");
        State state4 = new State("State 4");

        state0.setStartState(true);
        state2.setAcceptState(true);
        Range ziffernOhneNull = new Range('1', '9');
        Range ziffernMitNull = new Range('0', '9');

        addRangeTransition(state0, state1, ziffernOhneNull);
        addCharTransition(state0, state1, '+');
        addCharTransition(state0, state1, '-');
        addCharTransition(state0, state3, '0');
        addElseTransition(state0, state4);

        addCharTransition(state3, state2, ' ');
        addElseTransition(state3, state4);

        addRangeTransition(state1, state1, ziffernMitNull);
        addCharTransition(state1, state2, ' ');
        addElseTransition(state1, state4);

        addElseTransition(state4, state4);

        addState(state0);
        addState(state1);
        addState(state2);
        addState(state3);
        addState(state4);

        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.TYPE_INT;
    }
}
