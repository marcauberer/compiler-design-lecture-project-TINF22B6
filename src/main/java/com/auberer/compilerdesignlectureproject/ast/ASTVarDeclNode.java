package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ASTVarDeclNode extends ASTNode {

    String variableName;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVarDecl(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return ASTTypeNode.getSelectionSet();
    }

    public ASTTypeNode getDataType() {
        return getChild(ASTTypeNode.class, 0);
    }
    public ASTLogicalExprNode getLogicalExpr() {
        return getChild(ASTLogicalExprNode.class, 0);
    }
}