package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ASTCaseNode extends ASTNode{

    @Getter @Setter
    private CaseType caseType;

    public enum CaseType{
        INT_LIT,
        DOUBLE_LIT,
        STRING_LIT
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCase(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TokenType.TOK_CASE);
    }
}
