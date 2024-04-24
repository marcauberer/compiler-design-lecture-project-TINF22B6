package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTAfterIf extends ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTEPre.getSelectionSet());
        selectionSet.addAll(ASTEPost.getSelectionSet());

        return selectionSet;
    }
}

