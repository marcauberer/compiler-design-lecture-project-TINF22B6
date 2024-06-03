package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.sema.SuperType;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionDef{
    @Getter
    private final List<Pair<String, SuperType>> params;
    @Getter
    private final String name;
    @Getter
    private final SuperType returnType;

    public FunctionDef(List<Pair<String, SuperType>> params, String name, SuperType returnType){
        this.params = params;
        this.name = name;
        this.returnType = returnType;
    }

    public FunctionDef(String name, SuperType returnType){
        this.params = new ArrayList<>();
        this.name = name;
        this.returnType = returnType;
    }

    public FunctionDef(ASTFctDefNode node){
        returnType = node.getDataType().type.getSuperType();
        params = new ArrayList<>();
        node.getParams();
        name = node.getName();
    }

    public FunctionDef(ASTFctCallNode node){
        returnType = node.getType().getSuperType();
        node.getCallParams();
        params = new ArrayList<>();
        name = node.getName();
    }


    public boolean equals(FunctionDef def){
        boolean sameName = def.getName().equals(getName());
        boolean sameParams = params.equals(def.getParams());

        return sameName && sameParams;
    }

    @Data
    public static class Pair<A, B>{
        A first;
        B second;

        public Pair(A first, B second){
            this.first = first;
            this.second = second;
        }
    }

}
