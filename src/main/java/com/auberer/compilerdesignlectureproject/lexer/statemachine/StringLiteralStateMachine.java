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
        State state5 = new State("State 5");

        state0.setStartState(true);
        state4.setAcceptState(true);

        addCharTransition(state0, state1,'"');
        addElseTransition(state0, state5);

        // Betrachtung von escape-ten Anführungszeichen

        addCharTransition(state1, state2,'\\');
        addCharTransition(state1, state3,'"');
        addElseTransition(state1, state1);

        addCharTransition(state2, state1, '"');
        addElseTransition(state2, state1);

        addCharTransition(state3, state4, ' ');
        addElseTransition(state3, state5);

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
        return isInAcceptState() ? TokenType.TYPE_STRING : TokenType.TOK_INVALID;
    }
}
