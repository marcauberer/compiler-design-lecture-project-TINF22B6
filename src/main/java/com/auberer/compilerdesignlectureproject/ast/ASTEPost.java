package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTEPost extends ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }

    public static Set<TokenType> getSelectionSet() {

        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTIFStmtNode.getSelectionSet());
        selectionSet.addAll(ASTElse.getSelectionSet());

        return selectionSet;
    }

}
