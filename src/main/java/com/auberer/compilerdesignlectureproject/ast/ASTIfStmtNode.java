package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ASTIfStmtNode extends ASTNode {

    private boolean hasAfterIf;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIf(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TokenType.TOK_IF);
    }

    public ASTLogicalExprNode getCondition() {
        return getChild(ASTLogicalExprNode.class, 0);
    }

    public ASTStmtLstNode getBody() {
        return getChild(ASTStmtLstNode.class, 0);
    }

    public ASTAfterIfNode getAfterIf() {
        return getChild(ASTAfterIfNode.class, 0);
    }
}
