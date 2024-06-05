package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

import static com.auberer.compilerdesignlectureproject.lexer.TokenType.TOK_CALL;

@Getter
@Setter
public class ASTFctCallNode extends ASTNode {

    String name;

    @Accessors(fluent = true)
    boolean hasArgs;

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
}
