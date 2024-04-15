package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class KeywordStateMachine extends StateMachine{
    private final String keyword;

    public KeywordStateMachine(String keyword){
        this.keyword = keyword;
    }
    @Override
    public void init() {
        List<State> states = new ArrayList<>();
        State startState = new State("startState");
        State incorrectState = new State("incorrectState");
        State acceptedState = new State("acceptedState");

        startState.setStartState(true);
        acceptedState.setAcceptState(true);

        addState(startState);
        addState(acceptedState);
        addState(incorrectState);

        states.add(startState);

        for (int i = 0; i < this.keyword.length(); i++) {
            State state = new State("State " + keyword.charAt(i));
            addState(state);
            states.add(state);
        }

        for (int i = 0; i < states.size(); i++) {
            State currentState = states.get(i);
            State nextState;
            if(i == states.size()-1){
                nextState = acceptedState;
                addCharTransition(currentState, nextState, ' ');
            }else{
                nextState =  states.get(i+1);
                addCharTransition(currentState, nextState, this.keyword.charAt(i));
            }
            addElseTransition(currentState, incorrectState);
        }

        addElseTransition(incorrectState, incorrectState);
        reset();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.KEYWORD;
    }
}
