package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTAssignExprNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {

        return visitor.visitAssignExpr(this);
    }

    String member;

    public void setMember(String member){
        this.member = member;
    }



    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> identifier = ASTLogicalExprNode.getSelectionSet();
        identifier.add(TokenType.TOK_IDENTIFIER);

        return identifier;


    }
}