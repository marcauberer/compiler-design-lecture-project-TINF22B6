package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static com.auberer.compilerdesignlectureproject.lexer.TokenType.TOK_FUNC;

@Getter
@Setter
public class ASTFctDefNode extends ASTNode {

    String name;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFctDef(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TOK_FUNC);
    }

    public ASTTypeNode getType(){
        return getChild(ASTTypeNode.class,0);
    }

    public ASTParamLstNode getParams(){
        return getChild(ASTParamLstNode.class,0);
    }
    public ASTStmtLstNode getBody(){
        return getChild(ASTStmtLstNode.class,0);
    }

}
