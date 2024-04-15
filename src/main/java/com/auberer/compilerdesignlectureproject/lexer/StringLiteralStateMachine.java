package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;

public class StringLiteralStateMachine extends StateMachine {

    @Override
    public void init() {
        State startState = new State("START");
        addState(startState);
        startState.setStartState(true);

        State contentState = new State("CONTENT");
        addState(contentState);

        State endState = new State("END");
        addState(endState);
        endState.setAcceptState(true);

        Range charWithoutMark1 = new Range(' ', '!');
        Range charWithoutMark2 = new Range('#', '~');

        addCharTransition(startState, contentState, '"');
        addRangeTransition(contentState, contentState, charWithoutMark1);
        addRangeTransition(contentState, contentState, charWithoutMark2);
        addCharTransition(contentState, endState, '"');
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_STRING;
        } else {
            return TokenType.TOK_INVALID;
        }
    }

}
