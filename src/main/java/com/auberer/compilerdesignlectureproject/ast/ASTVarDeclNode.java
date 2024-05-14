package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ASTVarDeclNode extends ASTNode{

    @Getter
    @Setter
    String variableName;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVarDecl(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return  ASTTypeNode.getSelectionSet();
    }
}