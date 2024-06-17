package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ASTParamNode extends ASTNode{


    private String name;
    private SymbolTableEntry symbol;

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
