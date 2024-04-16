package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class KeywordStateMachine extends StateMachine {
    private final String keyword;

    public KeywordStateMachine(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void init() {
        State startState = new State("startState-Keyword");
        State incorrectState = new State("incorrectState-Keyword");

        startState.setStartState(true);

        addState(startState);
        addState(incorrectState);
        addElseTransition(incorrectState, incorrectState);

        List<State> states = new ArrayList<>();
        for (int i = 0; i < keyword.length(); i++) {
            State tmpState = new State(keyword.substring(i, i + 1));
            if (i == keyword.length() - 1) {
                tmpState.setAcceptState(true);
            }
            addState(tmpState);
            states.add(tmpState);
            if (i == 0) {
                addCharTransition(startState, tmpState, keyword.charAt(i));
                addElseTransition(startState, incorrectState);
            } else {
                addCharTransition(states.get(i - 1), states.get(i), keyword.charAt(i));
                addElseTransition(states.get(i - 1), incorrectState);
            }
        }

    }

    @Override
    public TokenType getTokenType() {
        return TokenType.KEYWORD;
    }
}
