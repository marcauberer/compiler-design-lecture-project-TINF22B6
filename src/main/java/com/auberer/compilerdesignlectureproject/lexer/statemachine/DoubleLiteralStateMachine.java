package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class DoubleLiteralStateMachine extends StateMachine {
    @Override
    public void init() {
        State startState = new State("Cool State");
        State isNumberState1 = new State("isNumberState1");// vor punkt
        State isNumberState2 = new State("isNumberState2");// nachpunkt
        State dotState = new State("DotState");
        State acceptingState = new State("acceptingState");
        State failState = new State("failState");

        // int sum= 5+9;
        // int sum = 5_900;
        // int sum= 50;
        //
        // = + ;

        startState.setStartState(true);
        acceptingState.setAcceptState(true);
        Range ziffern = new Range('0', '9');

        addState(startState);
        addState(isNumberState1);
        addState(isNumberState2);
        addState(acceptingState);
        addState(failState);

        addRangeTransition(startState, isNumberState1, ziffern);
        addElseTransition(startState, failState);

        addRangeTransition(isNumberState1, isNumberState1, ziffern);
        addCharTransition(isNumberState1, dotState, '.');
        addElseTransition(isNumberState1, acceptingState);

        addElseTransition(dotState, failState);

        addRangeTransition(dotState, isNumberState2, ziffern);
        addElseTransition(isNumberState2, acceptingState);
        // addElseTransition(acceptingState, failState);

        addElseTransition(failState, failState);

        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.DOUBLE;
    }
}
