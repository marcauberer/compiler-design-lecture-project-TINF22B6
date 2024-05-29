package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ASTParamLstNode extends ASTNode {

    List<String> paramNames = new ArrayList<>();

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitParamLst(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return ASTTypeNode.getSelectionSet();
    }

    public void addParamName(String paramNames) {
        this.paramNames.add(paramNames);
    }
}
