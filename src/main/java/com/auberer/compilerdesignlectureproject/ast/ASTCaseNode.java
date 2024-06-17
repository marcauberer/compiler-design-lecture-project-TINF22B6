package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

public class ASTCaseNode extends ASTNode{

    @Getter @Setter
    private CaseType caseType;

    @Getter @Setter
    private String caseLiteral;

    @Getter @Setter
    private CaseType expectedType;

    public enum CaseType{
        INT_LIT,
        DOUBLE_LIT,
        STRING_LIT,
        INVALID
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCase(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TokenType.TOK_CASE);
    }

    public ASTStmtLstNode getStmtList(){
        return getChild(ASTStmtLstNode.class, 0);
    }
}
