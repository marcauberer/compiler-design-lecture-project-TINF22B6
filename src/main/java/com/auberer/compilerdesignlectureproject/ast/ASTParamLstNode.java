package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.sema.SuperType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ASTParamLstNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitParamLst(this);
    }

    public List<ASTParamNode> getParamNodes() {
        return getChildren(ASTParamNode.class);
    }

    public static Set<TokenType> getSelectionSet() {
        return ASTTypeNode.getSelectionSet();
    }

}
