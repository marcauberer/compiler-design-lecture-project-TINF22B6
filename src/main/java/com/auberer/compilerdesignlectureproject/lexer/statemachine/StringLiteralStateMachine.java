package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class StringLiteralStateMachine extends StateMachine{

    @Override
    public void init() {
        State state0 = new State("State 0");
        State state1 = new State("State 1");
        State state2 = new State("State 2");
        State state3 = new State("State 3");
        State state4 = new State("State 4");

        state0.setStartState(true);
        state3.setAcceptState(true);

        addCharTransition(state0, state1,'"');
        addElseTransition(state0, state4);

        // Betrachtung von escape-ten Anführungszeichen

        addCharTransition(state1, state2,'\\');
        addCharTransition(state1, state4,'"');
        addElseTransition(state1, state1);

        addCharTransition(state2, state1, '"');
        addElseTransition(state2, state1);

        addCharTransition(state3, state3, ' ');
        addElseTransition(state3, state4);

        addState(state0);
        addState(state1);
        addState(state2);
        addState(state3);
        addState(state4);
        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.TYPE_STRING;
    }
}
