package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

public class IdentifierStateMachine extends StateMachine {

    @Override
    public void init() {
        State startState = new State("START");
        addState(startState);
        startState.setStartState(true);

        State identifierState = new State("IDENTIFIER");
        addState(identifierState);
        identifierState.setAcceptState(true);

        Range lowerCaseLetterRange = new Range('a', 'z');
        Range upperCaseLetterRange = new Range('A', 'Z');
        Range digetRange = new Range('0', '9');

        addCharTransition(startState, identifierState, '_');
        addCharTransition(startState, identifierState, '$');
        addRangeTransition(startState, identifierState, lowerCaseLetterRange);
        addRangeTransition(startState, identifierState, upperCaseLetterRange);
        addRangeTransition(identifierState, identifierState, lowerCaseLetterRange);
        addRangeTransition(identifierState, identifierState, upperCaseLetterRange);
        addRangeTransition(identifierState, identifierState, digetRange);
        addCharTransition(identifierState, identifierState, '_');
        addCharTransition(identifierState, identifierState, '$');
    }

    @Override
    public TokenType getTokenType() {
        if (isInAcceptState()) {
            return TokenType.TOK_IDENTIFIER;
        } else {
            return TokenType.TOK_INVALID;
        }
    }
}
