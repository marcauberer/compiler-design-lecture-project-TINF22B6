package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ASTParamNode extends ASTNode{

    @Getter
    @Setter
    private String name;

    public static Set<TokenType> getSelectionSet() {
        return ASTTypeNode.getSelectionSet();
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitParam(this);
    }

    public ASTTypeNode getDataType(){
        return getChild(ASTTypeNode.class,0);
    }


}
