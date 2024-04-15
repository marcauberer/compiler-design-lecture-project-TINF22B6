package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class DoubleLiteralStateMachine extends StateMachine{
    @Override
    public void init() {
        State state0 = new State("State 0");
        State state1 = new State("State 1");
        State state2 = new State("State 2");
        State state3 = new State("State 3");
        State state4 = new State("State 4");
        State state5 = new State("State 5");

        state0.setStartState(true);
        state2.setAcceptState(true);

        Range ziffernOhneNull = new Range('1', '9');
        Range ziffernMitNull = new Range('0', '9');

        addRangeTransition(state0, state1, ziffernOhneNull);
        addCharTransition(state0, state1, '+');
        addCharTransition(state0, state1, '-');
        addCharTransition(state0, state3, '0');
        addElseTransition(state0, state5);

        addCharTransition(state3, state2, '0');
        addElseTransition(state3, state5);

        addRangeTransition(state1, state1, ziffernMitNull);
        addCharTransition(state1, state4, '.');
        addCharTransition(state1, state2, ' ');
        addElseTransition(state1, state5);

        addRangeTransition(state4, state1, ziffernMitNull);
        addCharTransition(state4, state2, ' ');
        addElseTransition(state4, state5);

        addElseTransition(state5, state5);

        addState(state0);
        addState(state1);
        addState(state2);
        addState(state3);
        addState(state4);
        addState(state5);

        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.TYPE_DOUBLE;
    }
}
