package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTLogicalExpressionNode extends ASTNode{

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogicalExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(
                TokenType.TOK_PLUS,
                TokenType.TOK_MINUS,
                TokenType.TOK_INT_LIT,
                TokenType.TOK_DOUBLE_LIT,
                TokenType.TOK_STRING_LIT,
                TokenType.TOK_IDENTIFIER,
                TokenType.TOK_CALL,
                TokenType.TOK_PRINT,
                TokenType.TOK_LPAREN
        );
    }
}
