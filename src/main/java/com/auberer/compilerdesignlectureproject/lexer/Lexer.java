package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.IntegerLiteralStateMachine;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.KeywordStateMachine;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.IReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lexer implements ILexer {
    private final IReader reader;
    private final List<StateMachine> stateMachineList;
    private Token currentToken;

    public Lexer(IReader reader) {
        this.reader = reader;
        this.stateMachineList = new ArrayList<>();
        // stateMachineList.add(new KeywordStateMachine("DankeRoethig"));
        // stateMachineList.add(new StringLiteralStateMachine());
        stateMachineList.add(new KeywordStateMachine("XSLT"));
        // stateMachineList.add(new KeywordStateMachine("XML"));
        // stateMachineList.add(new DoubleLiteralStateMachine());
        stateMachineList.add(new IntegerLiteralStateMachine());

        for (StateMachine stateMachine : stateMachineList) {
            stateMachine.init();
            stateMachine.reset();
        }
    }

    @Override
    public Token getToken() {
        return currentToken;
    }

    @Override
    public void advance() {
        StringBuilder currentTokenText = new StringBuilder();

        while (!reader.isEOF()) {
            currentTokenText.append(reader.getChar());
            for (StateMachine machine : stateMachineList) {
                machine.processInput(reader.getChar());
                currentToken = new Token(machine.getTokenType(), currentTokenText.toString().substring(0, currentTokenText.toString().length()), reader.getCodeLoc());
                if (machine.isInAcceptState()) {
                    break;
                }
            }
            reader.advance();
        }
    }

    @Override
    public void expect(TokenType expectedType) {
        if (currentToken.getType() != expectedType) {
            throw new IllegalStateException("Expected " + expectedType + " but got " + currentToken.getType());
        }
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) {
        for (TokenType expectedType : expectedTypes) {
            expect(expectedType);
        }
    }

    @Override
    public boolean isEOF() {
        return reader.isEOF();
    }

    @Override
    public CodeLoc getCodeLoc() {
        return reader.getCodeLoc();
    }
}
