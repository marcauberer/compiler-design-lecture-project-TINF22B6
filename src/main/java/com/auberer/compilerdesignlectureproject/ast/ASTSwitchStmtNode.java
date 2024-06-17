package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.List;
import java.util.Set;

public class ASTSwitchStmtNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSwitchStmt(this);
    }

    public static Set<TokenType> getSelectionSet(){
        return Set.of(TokenType.TOK_SWITCH);
    }

    public List<ASTCaseNode> getCases(){
        return getChildren(ASTCaseNode.class);
    }

    public ASTDefaultNode getDefault(){
        try{
            return getChild(ASTDefaultNode.class, 0);
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public ASTLogicalExprNode getLogicalExpr(){
        return getChild(ASTLogicalExprNode.class, 0);
    }


}
