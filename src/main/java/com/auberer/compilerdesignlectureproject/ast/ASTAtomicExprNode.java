package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class ASTAtomicExprNode extends ASTNode{
    @Setter
    public static AtomicOperator operator;

    @Setter
    public static int int_lit;

    @Setter
    public static double double_lit;

    @Setter
    public static String string_lit;

    @Setter
    public static boolean true_or_false;

    @Setter
    public static String identifier;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAtomicExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(
                TokenType.TOK_INT_LIT,
                TokenType.TOK_DOUBLE_LIT,
                TokenType.TOK_STRING_LIT,
                TokenType.TOK_IDENTIFIER,
                TokenType.TOK_CALL,
                TokenType.TOK_PRINT,
                TokenType.TOK_LPAREN
        );
    }

    public enum AtomicOperator {
        INT_LIT,
        DOUBLE_LIT,
        STRING_LIT,
        IDENTIFIER,
        TRUE,
        FALSE,
        FCT_CALL,
        PRINT_BUILT_IN_CALL,
        ASSIGN_EXPR
    }
}
