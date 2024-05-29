package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTNode;

public class SemaError extends RuntimeException {

    public SemaError(ASTNode node, String message) {
      super(node.getCodeLoc().toString() + ": " + message);
    }

    public SemaError(ASTNode node, String message, Throwable cause) {
      super(message, cause);
    }
}
