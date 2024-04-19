package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Lexer class for tokenizing the input stream.
 * Input: Character stream
 * Output: Token stream
 */
@Slf4j
public class Lexer implements ILexer {
  private final Reader reader;
  private final List<StateMachine> stateMachines = new ArrayList<>();
  private final Queue<Character> inputBuffer = new LinkedList<>();
  private Token curToken;

  public Lexer(Reader reader) {
    this.reader = reader;

    // Here, the order matters. The last state machine has the highest priority in case
    // multiple machines match the given input at the same length.
    stateMachines.add(new KeywordStateMachine("int", TokenType.TOK_TYPE_INT));
    stateMachines.add(new KeywordStateMachine("double", TokenType.TOK_TYPE_DOUBLE));
    stateMachines.add(new KeywordStateMachine("string", TokenType.TOK_TYPE_STRING));
    stateMachines.add(new KeywordStateMachine("empty", TokenType.TOK_TYPE_EMPTY));
    stateMachines.add(new KeywordStateMachine("if", TokenType.TOK_IF));
    stateMachines.add(new KeywordStateMachine("else", TokenType.TOK_ELSE));
    stateMachines.add(new KeywordStateMachine("while", TokenType.TOK_WHILE));
    stateMachines.add(new KeywordStateMachine("do", TokenType.TOK_DO));
    stateMachines.add(new KeywordStateMachine("for", TokenType.TOK_FOR));
    stateMachines.add(new KeywordStateMachine("func", TokenType.TOK_FUNC));
    stateMachines.add(new KeywordStateMachine("cnuf", TokenType.TOK_CNUF));
    stateMachines.add(new KeywordStateMachine("return", TokenType.TOK_RETURN));
    stateMachines.add(new KeywordStateMachine("switch", TokenType.TOK_SWITCH));
    stateMachines.add(new KeywordStateMachine("case", TokenType.TOK_CASE));
    stateMachines.add(new KeywordStateMachine("default", TokenType.TOK_DEFAULT));
    stateMachines.add(new KeywordStateMachine("call", TokenType.TOK_CALL));
    stateMachines.add(new KeywordStateMachine("print", TokenType.TOK_PRINT));
    stateMachines.add(new PunctuationStateMachine("{", TokenType.TOK_LBRACE));
    stateMachines.add(new PunctuationStateMachine("}", TokenType.TOK_RBRACE));
    stateMachines.add(new PunctuationStateMachine("(", TokenType.TOK_LPAREN));
    stateMachines.add(new PunctuationStateMachine(")", TokenType.TOK_RPAREN));
    stateMachines.add(new PunctuationStateMachine(",", TokenType.TOK_COMMA));
    stateMachines.add(new PunctuationStateMachine(":", TokenType.TOK_COLON));
    stateMachines.add(new PunctuationStateMachine("+", TokenType.TOK_PLUS));
    stateMachines.add(new PunctuationStateMachine("-", TokenType.TOK_MINUS));
    stateMachines.add(new PunctuationStateMachine("*", TokenType.TOK_MUL));
    stateMachines.add(new PunctuationStateMachine("/", TokenType.TOK_DIV));
    stateMachines.add(new PunctuationStateMachine("==", TokenType.TOK_EQUAL));
    stateMachines.add(new PunctuationStateMachine("!=", TokenType.TOK_NOT_EQUAL));
    stateMachines.add(new PunctuationStateMachine("&&", TokenType.TOK_LOGICAL_AND));
    stateMachines.add(new PunctuationStateMachine("||", TokenType.TOK_LOGICAL_OR));
    stateMachines.add(new PunctuationStateMachine(";", TokenType.TOK_SEMICOLON));
    stateMachines.add(new PunctuationStateMachine("=", TokenType.TOK_ASSIGN));
    stateMachines.add(new DoubleLiteralStateMachine());
    stateMachines.add(new IntegerLiteralStateMachine());
    stateMachines.add(new StringLiteralStateMachine());
    stateMachines.add(new IdentifierStateMachine());

    // Initialize all state machines
    for (StateMachine stateMachine : stateMachines)
      stateMachine.init();

    // Read first token
    advance();
  }

  @Override
  public Token getToken() {
    return curToken;
  }

  private char peekChar() {
    if (!inputBuffer.isEmpty())
      return inputBuffer.peek();
    return reader.getChar();
  }

  private char getCurChar() {
    // If there are characters in the input buffer, return the next one
    // This is required to backtrack to the position, where a previously matching state machine accepted
    // e.g. in case of the keyword machines "for" and "foreach", with the input "forea", the "for" machine
    // would accept first, but the "foreach" machine would continue matching. Later, the "foreach" machine
    // would fail. Then we want to produce the token "for" and continue with the "ea" part of the input.
    if (!inputBuffer.isEmpty())
      return inputBuffer.poll();
    char curChar = reader.getChar();
    reader.advance();
    return curChar;
  }

  @Override
  public void advance() {
    // Reset all state machines to start from the respective initial state
    for (StateMachine stateMachine : stateMachines)
      stateMachine.reset();

    // Skip any whitespaces
    while (!reader.isEOF() && Character.isWhitespace(peekChar()))
      getCurChar();

    CodeLoc tokenCodeLoc = reader.getCodeLoc();
    StringBuilder tokenText = new StringBuilder();

    // Run all state machines in parallel on the given char input stream
    List<StateMachine> runningMachines = new ArrayList<>(stateMachines);
    Map<StateMachine, Integer> acceptingMachines = new LinkedHashMap<>();
    Queue<Character> newInputBuffer = new LinkedList<>();
    while (!reader.isEOF() && !runningMachines.isEmpty()) {
      char curChar = getCurChar();
      newInputBuffer.add(curChar);
      tokenText.append(curChar);

      for (StateMachine stateMachine : new CopyOnWriteArrayList<>(runningMachines)) {
        // Try to process the input. If the processing throws an exception, the machine is in an invalid state
        // and should be removed from the list of running machines.
        try {
          stateMachine.processInput(curChar);
        } catch (IllegalStateException e) {
          log.debug("State machine does not match input {}: {}", tokenText, e.getMessage());
          runningMachines.remove(stateMachine);
          continue;
        }

        // If the machine is in an accept state, add it to the list of accepting machines
        if (stateMachine.isInAcceptState()) {
          acceptingMachines.remove(stateMachine);
          acceptingMachines.put(stateMachine, tokenText.length());
          // Clear input buffer to make sure we backtrack to this point in the input in case
          // no other running machine accepts later.
          newInputBuffer.clear();
        }
      }
    }

    // Add the remaining characters to the input buffer
    inputBuffer.addAll(newInputBuffer);

    // If EOF is reached, finalize the token
    if (acceptingMachines.isEmpty()) {
      curToken = new Token(TokenType.TOK_INVALID, "", tokenCodeLoc);
      return;
    }

    // Check which of the running machines has the highest priority and set the current token accordingly
    Map.Entry<StateMachine, Integer> winningEntry = null;
    for (Map.Entry<StateMachine, Integer> entry : acceptingMachines.entrySet())
      if (winningEntry == null || entry.getValue().compareTo(winningEntry.getValue()) > 0)
        winningEntry = entry;
    StateMachine winningMachine = winningEntry.getKey();
    String tokenTextString = tokenText.substring(0, tokenText.length() - 1);
    curToken = new Token(winningMachine.getTokenType(), tokenTextString, tokenCodeLoc);
  }

  @Override
  public void expect(TokenType expectedType) throws RuntimeException {
    if (curToken.getType() != expectedType)
      throw new RuntimeException("Unexpected token: " + curToken.getType() + " at " + curToken.getCodeLoc() + ". Expected: " + expectedType);
    advance();
  }

  @Override
  public void expectOneOf(Set<TokenType> expectedTypes) throws RuntimeException {
    if (!expectedTypes.contains(curToken.getType()))
      throw new RuntimeException("Unexpected token: " + curToken.getType() + " at " + curToken.getCodeLoc() + ". Expected one of: " + expectedTypes);
  }

  @Override
  public boolean isEOF() {
    return reader.isEOF();
  }

  @Override
  public CodeLoc getCodeLoc() {
    return reader.getCodeLoc();
  }
}
