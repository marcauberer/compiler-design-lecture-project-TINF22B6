package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.sema.SuperType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class FunctionDef{
    private final List<SuperType> params;
    private final String name;
    private final SuperType returnType;

    public FunctionDef(List<SuperType> params, String name, SuperType returnType){
        this.params = params;
        this.name = name;
        this.returnType = returnType;
    }

    public FunctionDef(String name, SuperType returnType){
        this.params = new ArrayList<>();
        this.name = name;
        this.returnType = returnType;
    }

    public FunctionDef createNewDefWithType(SuperType returnType){
        return new FunctionDef(this.params, this.getName(), returnType);
    }

    public FunctionDef(ASTFctDefNode node){
        returnType = node.getDataType().type.getSuperType();
        params = node.getParams().getParamNodes().stream().map(astParamNode ->
                astParamNode.getType().getSuperType()).toList();
        name = node.getName();
    }

    public FunctionDef(ASTFctCallNode node){
        returnType = node.getType().getSuperType();
        params = node.getCallParams().getParamsAsLogicNodes().stream().map(astLogicalExprNode ->
                astLogicalExprNode.getType().getSuperType()).toList();
        name = node.getName();
    }


    public boolean equals(FunctionDef def){
        return def.getName().equals(getName()) && isSameParams(def.getParams());
    }

    public boolean isSameParams(List<SuperType> otherParams){
        if(params.size() != otherParams.size()){
            return false;
        }
        for(int i = 0; i < params.size(); i++){
            if(!params.get(i).equals(otherParams.get(i))){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        String s =  "name: " + name + ", params: ";
        for(SuperType param : params){
            s += " [" + param.toString() + "] ";
        }
        return s;
    }

    @Data
    public static class Pair<A, B>{
        A first;
        B second;

        public Pair(A first, B second){
            this.first = first;
            this.second = second;
        }

        public boolean equals(Pair<A, B> pair){
            return first.equals(pair.first) && second.equals(pair.second);
        }
    }

}
