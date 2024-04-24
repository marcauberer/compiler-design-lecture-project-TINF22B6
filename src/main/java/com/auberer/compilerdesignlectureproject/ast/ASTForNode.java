package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import java.util.HashSet;
import java.util.Set;

public class ASTForNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFor(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> followSet = Set.of(TokenType.TOK_RBRACE, TokenType.TOK_DEFAULT, TokenType.TOK_RETURN);

        Set<TokenType> selectionSet = new HashSet<>(ASTStmtNode.getSelectionSet());

        Set<TokenType> forLoopSelectionSet = new HashSet<>();
        forLoopSelectionSet.add(TokenType.TOK_FOR);
        selectionSet.addAll(forLoopSelectionSet);

        selectionSet.addAll(followSet);
        return selectionSet;
    }

}
