package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTNode;
import com.auberer.compilerdesignlectureproject.ast.ASTPrintBuiltinCallNode;
import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Stack;

public class Parser implements IParser {

  // Lexer interface that can be used to accept the given input
  ILexer lexer;
  // Stack to keep track of the parent nodes
  Stack<ASTNode> parentStack;

  @Override
  public ASTEntryNode parse() {
    ASTEntryNode node = new ASTEntryNode();
    enterNode(node);

    // Parse all the tokens until the end of the input file
    while (!lexer.isEOF()) {
      // ToDo: Uncomment the following line as soon as function definitions work
      // parseFctDef();
    }

    exitNode(node);
    return node;
  }

  // ToDo: Add more parse methods here

  public ASTPrintBuiltinCallNode parsePrintBuiltin() {
    ASTPrintBuiltinCallNode node = new ASTPrintBuiltinCallNode();
    enterNode(node);

    // Parse the print builtin
    lexer.expect(TokenType.TOK_PRINT);
    lexer.expect(TokenType.TOK_LPAREN);
    // ToDo: Uncomment as soon as the expression loop works
    // parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);

    exitNode(node);
    return node;
  }

  private void enterNode(ASTNode node) {
    if (!parentStack.isEmpty()) {
      // Make sure the node is not pushed twice
      assert parentStack.peek() != node;
      // Link parent and child nodes, so we can traverse the tree
      ASTNode parent = parentStack.peek();
      parent.addChild(node);
      node.setParent(parent);
    }
    // Push the node to the stack
    parentStack.push(node);
  }

  private void exitNode(ASTNode node) {
    // Make sure the node is the last one pushed
    assert parentStack.peek() == node;
    // Remove the node from the stack
    parentStack.pop();
  }
}
