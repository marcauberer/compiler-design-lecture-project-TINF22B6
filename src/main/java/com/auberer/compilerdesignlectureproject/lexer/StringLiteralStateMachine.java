package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

public class StringLiteralStateMachine extends StateMachine {

    @Override
    public void init() {

    }

    @Override
    public TokenType getTokenType() {
        return TokenType.TOK_STRING;
    }
}
