package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IdentifierStateMachine extends StateMachine{
    @Override
    public void init() {
        State state0 = new State("State 0");
        State state1 = new State("State 1");
        State state2 = new State("State 2");
        State state3 = new State("State 3");

        state0.setStartState(true);
        state2.setAcceptState(true);

        Range kleinbuchstaben = new Range('a','z');
        Range grossbuchstaben = new Range('A','Z');
        Range ziffern = new Range('0','9');

        addRangeTransition(state0, state1, kleinbuchstaben);
        addRangeTransition(state0, state1, grossbuchstaben);
        addCharTransition(state0, state1, '_');
        addElseTransition(state0, state3);

        addRangeTransition(state1, state1, kleinbuchstaben);
        addRangeTransition(state1, state1, grossbuchstaben);
        addRangeTransition(state1, state1, ziffern);
        addCharTransition(state1, state2, ' ');
        addElseTransition(state1, state3);

        addElseTransition(state3, state3);

        addState(state0);
        addState(state1);
        addState(state2);
        addState(state3);

        reset();
    }

    @Override
    public TokenType getTokenType() {
        return isInAcceptState() ? TokenType.IDENTIFIER : TokenType.TOK_INVALID;
    }
}
