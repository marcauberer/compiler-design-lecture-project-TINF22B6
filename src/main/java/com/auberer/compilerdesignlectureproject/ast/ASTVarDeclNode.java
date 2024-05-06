package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTVarDeclNode extends ASTNode{

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVarDeclNode(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(
                TokenType.TOK_TYPE_INT,
                TokenType.TOK_TYPE_DOUBLE,
                TokenType.TOK_TYPE_STRING,
                TokenType.TOK_TYPE_EMPTY);
    }


}
