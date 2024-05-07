package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

import static com.auberer.compilerdesignlectureproject.lexer.TokenType.TOK_FUNC;

public class ASTFctDefNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFctDef(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TOK_FUNC);
    }
}
