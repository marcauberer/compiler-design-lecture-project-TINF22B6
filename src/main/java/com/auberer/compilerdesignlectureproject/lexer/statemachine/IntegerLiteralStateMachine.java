package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IntegerLiteralStateMachine extends StateMachine {
    @Override
    public void init() {
        State startState = new State("Start State");
        State isNumberState = new State("State 1");
        State acceptingState = new State("State 2");
        State failState = new State("Not Accepting");

        // int sum= 5+9;
        // int sum = 5_900;
        // int sum= 50;
        //
        // = + ;

        startState.setStartState(true);
        acceptingState.setAcceptState(true);
        Range ziffern = new Range('0', '9');

        addState(startState);
        addState(isNumberState);
        addState(acceptingState);
        addState(failState);


        addRangeTransition(startState, isNumberState, ziffern);
        addCharTransition(startState, isNumberState, '5');
        addElseTransition(startState, failState);


        addRangeTransition(isNumberState, isNumberState, ziffern);
        addElseTransition(isNumberState, acceptingState);

        addElseTransition(acceptingState, failState);

        addElseTransition(failState, failState);



        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.INTEGER;
    }
}
