package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.*;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.IReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lexer implements ILexer {
    private final IReader reader;
    private Token currToken;
    private final List<StateMachine> stateMachines = new ArrayList<>();

    public Lexer(IReader reader) {
        this.reader = reader;
        stateMachines.add(new StringLiteralStateMachine());
        stateMachines.add(new IntegerLiteralStateMachine());
        stateMachines.add(new DoubleLiteralStateMachine());
        stateMachines.add(new IdentifierStateMachine());

        /*stateMachines.add(new KeywordStateMachine("public"));
        stateMachines.add(new KeywordStateMachine("private"));
        stateMachines.add(new KeywordStateMachine("class"));
        stateMachines.add(new KeywordStateMachine("static"));
        stateMachines.add(new KeywordStateMachine("final"));
        stateMachines.add(new KeywordStateMachine("abstract"));
        stateMachines.add(new KeywordStateMachine("interface"));
        stateMachines.add(new KeywordStateMachine("enum"));
        */

        for (StateMachine stateMachine : stateMachines) {
            stateMachine.init();
        }
    }

    @Override
    public Token getToken() {
        return this.currToken;
    }

    @Override
    public void advance() {
        boolean machineAccepted = false;
        StringBuilder currentTokenText = new StringBuilder();

        while(!machineAccepted){
            currentTokenText.append(reader.getChar());
            for (StateMachine stateMachine : stateMachines) {
                stateMachine.processInput(reader.getChar());
                if(stateMachine.isInAcceptState()) machineAccepted = true;
            }
            reader.advance();
        }

        for (StateMachine stateMachine : stateMachines) {
            if(stateMachine.isInAcceptState()){
                this.currToken = new Token(stateMachine.getTokenType(), currentTokenText.toString(), reader.getCodeLoc());
            }
            stateMachine.reset();
        }
    }

    @Override
    public void expect(TokenType expectedType) {
        if (this.currToken.getType() != expectedType) {
            throw new RuntimeException("Expected " + expectedType + " but got " + this.currToken.getType());
        }
        this.advance();
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