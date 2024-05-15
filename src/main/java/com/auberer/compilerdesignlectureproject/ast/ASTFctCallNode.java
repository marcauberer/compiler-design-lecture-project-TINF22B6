package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

import static com.auberer.compilerdesignlectureproject.lexer.TokenType.TOK_CALL;

public class ASTFctCallNode extends ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFctCall(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TOK_CALL);
    }

    public ASTCallParamsNode getCallParams(){
        return getChild(ASTCallParamsNode.class, 0);
    }

    public ASTFctDefNode getDefinition(){
        return getChild(ASTFctDefNode.class, 0);
    }
}
