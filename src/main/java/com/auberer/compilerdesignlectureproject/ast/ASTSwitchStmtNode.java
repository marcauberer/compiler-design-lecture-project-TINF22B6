package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.Set;

public class ASTSwitchStmtNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSwitchStmt(this);
    }

    public static Set<TokenType> getSelectionSet(){
        return Set.of(TokenType.TOK_SWITCH);
    }

    public ASTCasesNode getCases(){
        return getChild(ASTCasesNode.class, 0);
    }

    public ASTDefaultNode getDefault(){
        ArrayList<ASTDefaultNode> child = getChildren(ASTDefaultNode.class);
        if(!child.isEmpty()){
            return child.get(0);
        }
        return null;
    }

    public ASTLogicalExprNode getLogicalExpr(){
        return getChild(ASTLogicalExprNode.class, 0);
    }


}
