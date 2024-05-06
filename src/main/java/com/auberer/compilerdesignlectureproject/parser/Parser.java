package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.Stack;

@Slf4j
@NoArgsConstructor
public class Parser implements IParser {

  // Lexer interface that can be used to accept the given input
  ILexer lexer;
  // Stack to keep track of the parent nodes
  Stack<ASTNode> parentStack = new Stack<>();

  public Parser(ILexer lexer) {
    this.lexer = lexer;
  }

  /**
   * Entry point to the parser. This method should parse the input file and return the root node of the AST.
   *
   * @return AST root node
   */
  @Override
  public ASTEntryNode parse() {
    ASTEntryNode node = new ASTEntryNode();
    enterNode(node);

    // Parse all the tokens until the end of the input file
    while (!lexer.isEOF()) {
      // ToDo: Team 6: Uncomment the following line as soon as function definitions work
      // parseFctDef();
    }

    exitNode(node);
    return node;
  }

  public ASTForNode parseForLoop() throws Exception {
    ASTForNode node = new ASTForNode();

    enterNode(node);

    Set<TokenType> forSelectionSet = ASTForNode.getSelectionSet();

    lexer.expect(TokenType.TOK_FOR);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_SEMICOLON);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_SEMICOLON);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);
    lexer.expect(TokenType.TOK_LBRACE);
    //parseStmtLst();
    lexer.expect(TokenType.TOK_RBRACE);
    exitNode(node);
    return node;
  }

  public ASTStmtLstNode parseStmtLst() {
    ASTStmtLstNode node = new ASTStmtLstNode();
    enterNode(node);

    // Parse all statements until the end of the statement list
    Set<TokenType> stmtLstSelectionSet = ASTStmtLstNode.getSelectionSet();
    Set<TokenType> stmtSelectionSet = ASTStmtNode.getSelectionSet();
    while (!stmtLstSelectionSet.contains(lexer.getToken().getType())) {
      if (stmtSelectionSet.contains(lexer.getToken().getType())) {
        parseStmt();
      } else {
        assert false : "Unexpected token in statement list";
      }
    }

    exitNode(node);
    return node;
  }

  public ASTStmtNode parseStmt() {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node);

    // Parse the statement
    // ToDo: Team 7: Uncomment as soon as varDecl and assignExpr work
    /*Set<TokenType> varDeclSelectionSet = ASTVarDeclNode.getSelectionSet();
    Set<TokenType> assignExprSelectionSet = ASTAssignExprNode.getSelectionSet();
    if (varDeclSelectionSet.contains(lexer.getToken().getType())) {
      parseVarDecl();
    } else if (assignExprSelectionSet.contains(lexer.getToken().getType())) {
      parseAssignExpr();
    } else {
      log.error("Unexpected token in statement");
    }*/

    exitNode(node);
    return node;
  }

  public ASTPrintBuiltinCallNode parsePrintBuiltinCall() throws Exception {
    ASTPrintBuiltinCallNode node = new ASTPrintBuiltinCallNode();
    enterNode(node);

    // Parse the print builtin
    lexer.expect(TokenType.TOK_PRINT);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);

    exitNode(node);
    return node;
  }

  public ASTTypeNode parseType() throws Exception {
    ASTTypeNode node = new ASTTypeNode();
    enterNode(node);

    // Parse the type
    if (lexer.getToken().getType() == TokenType.TOK_TYPE_INT) {
      lexer.expect(TokenType.TOK_TYPE_INT);
      node.setType(ASTTypeNode.DataType.INT);
    } else if (lexer.getToken().getType() == TokenType.TOK_TYPE_DOUBLE) {
      lexer.expect(TokenType.TOK_TYPE_DOUBLE);
      node.setType(ASTTypeNode.DataType.DOUBLE);
    } else if (lexer.getToken().getType() == TokenType.TOK_TYPE_STRING) {
      lexer.expect(TokenType.TOK_TYPE_STRING);
      node.setType(ASTTypeNode.DataType.STRING);
    } else if (lexer.getToken().getType() == TokenType.TOK_TYPE_EMPTY) {
      lexer.expect(TokenType.TOK_TYPE_EMPTY);
      node.setType(ASTTypeNode.DataType.EMPTY);
    } else {
      assert false : "Unexpected token in type";
    }

    exitNode(node);
    return node;
  }

  // ToDo: Add more parse methods here

  // ToDo: Method stub for other teams to rely on. Team 7: Implement this method
  public void parseAssignExpr() {
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
