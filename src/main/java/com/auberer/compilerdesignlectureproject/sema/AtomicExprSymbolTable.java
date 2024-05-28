package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;

import java.util.Stack;

public class AtomicExprSymbolTable extends SymbolTableBuilder{

    Stack<Scope> currentScopes = new Stack<>();

    @Override
    public Void visitEntry(ASTEntryNode node) {
        Scope rootScope = new Scope();
        currentScopes.push(rootScope);

        visitChildren(node);


        if (rootScope.lookupSymbol("identifier") == null)
            throw new SemaError("No identifier found");

        assert currentScopes.size() == 1 && currentScopes.peek() == rootScope;
        currentScopes.pop();
        return null;
    }
}

