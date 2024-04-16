package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateMachineTest {

  private static class ABStateMachine extends StateMachine {
    @Override
    public void init() {
      // Start state
      State stateStart = new State("Start");
      stateStart.setStartState(true);
      addState(stateStart);
      // State A
      State stateA = new State("A");
      addState(stateA);
      // State B
      State stateB = new State("B");
      stateB.setAcceptState(true);
      addState(stateB);
      // Transitions
      addCharTransition(stateStart, stateA, 'a');
      addCharTransition(stateA, stateB, 'b');
      addElseTransition(stateA, stateStart);
      addElseTransition(stateB, stateStart);
    }

    @Override
    public TokenType getTokenType() {
      return TokenType.TOK_INVALID;
    }
  }

  private static class AddAlphanumericStateMachine extends StateMachine {
    @Override
    public void init() {
      // Start state
      State stateStart = new State("Start");
      stateStart.setStartState(true);
      addState(stateStart);
      // State
      State stateAccept = new State("Accept");
      stateAccept.setAcceptState(true);
      addState(stateAccept);
      // Transitions
      addRangeTransition(stateStart, stateAccept, new Range('a', 'z'));
      addElseTransition(stateAccept, stateStart);
    }

    @Override
    public TokenType getTokenType() {
      return TokenType.TOK_INVALID;
    }
  }

  @Test
  @DisplayName("Test state machine initialization")
  public void testInit() {
    ABStateMachine stateMachine = new ABStateMachine();
    stateMachine.init();
    stateMachine.reset();
    assertEquals("Start", stateMachine.getCurrentState().getName());
  }

  @Test
  @DisplayName("Test state machine processing")
  public void testProcessInput() {
    ABStateMachine stateMachine = new ABStateMachine();
    stateMachine.init();
    stateMachine.reset();
    assertEquals("Start", stateMachine.getCurrentState().getName());
    // Move to state A
    stateMachine.processInput('a');
    assertEquals("A", stateMachine.getCurrentState().getName());
    // Move back to start state
    stateMachine.processInput('c');
    assertEquals("Start", stateMachine.getCurrentState().getName());
    // Move to state A again
    stateMachine.processInput('a');
    assertEquals("A", stateMachine.getCurrentState().getName());
    // Move to state B
    stateMachine.processInput('b');
    assertEquals("B", stateMachine.getCurrentState().getName());
    // Check if we are in the accept state now
    assertTrue(stateMachine.isInAcceptState());
  }

  @Test
  @DisplayName("Test state machine processing with range transition")
  public void testProcessInputRange() {
    AddAlphanumericStateMachine stateMachine = new AddAlphanumericStateMachine();
    stateMachine.init();
    stateMachine.reset();
    assertEquals("Start", stateMachine.getCurrentState().getName());
    // Move to accept state
    stateMachine.processInput('a');
    assertEquals("Accept", stateMachine.getCurrentState().getName());
    // Move back to start state
    stateMachine.processInput('1');
    assertEquals("Start", stateMachine.getCurrentState().getName());
    // Move to accept state again
    stateMachine.processInput('z');
    assertEquals("Accept", stateMachine.getCurrentState().getName());
    // Check if we are in the accept state now
    assertTrue(stateMachine.isInAcceptState());
  }

  @Test
  @DisplayName("Test dot code generation")
  public void testToDotCode() {
    ABStateMachine stateMachine = new ABStateMachine();
    stateMachine.init();
    stateMachine.reset();
    assertEquals("""
        digraph G {
        Start [label="Start"]
        A [label="A"]
        B [label="B"]
        Start -> A [label="a"]
        A -> B [label="b"]
        A -> Start [label="*"]
        B -> Start [label="*"]
        }""", stateMachine.getDotCode());
  }

  @Test
  @DisplayName("Test DoubleLiteralStateMachine")
  public void testDoubleLiteralStateMachine() {
    DoubleLiteralStateMachine doubleLiteralStateMachine = new DoubleLiteralStateMachine();
    doubleLiteralStateMachine.init();

    // Test input: 12345
    doubleLiteralStateMachine.processInput('1');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('2');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('3');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('5');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput(' ');
    assertEquals("State 2", doubleLiteralStateMachine.getCurrentState().getName());

    assertTrue(doubleLiteralStateMachine.isInAcceptState());
    doubleLiteralStateMachine.reset();

    // Test input: 12345.23
    doubleLiteralStateMachine.processInput('1');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('2');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('3');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('5');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('.');
    assertEquals("State 4", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('2');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('3');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput(' ');
    assertEquals("State 2", doubleLiteralStateMachine.getCurrentState().getName());

    assertTrue(doubleLiteralStateMachine.isInAcceptState());
    doubleLiteralStateMachine.reset();

    // Test input: 12.
    doubleLiteralStateMachine.processInput('1');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('2');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('.');
    assertEquals("State 4", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput(' ');
    assertEquals("State 2", doubleLiteralStateMachine.getCurrentState().getName());

    assertTrue(doubleLiteralStateMachine.isInAcceptState());
    doubleLiteralStateMachine.reset();

    // Test input: 1as
    doubleLiteralStateMachine.processInput('1');
    assertEquals("State 1", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('a');
    assertEquals("State 5", doubleLiteralStateMachine.getCurrentState().getName());
    doubleLiteralStateMachine.processInput('s');
    assertEquals("State 5", doubleLiteralStateMachine.getCurrentState().getName());

    assertFalse(doubleLiteralStateMachine.isInAcceptState());
  }

  @Test
  @DisplayName("Test IntegerLiteralStateMachine")
  public void testIntegerLiteralStateMachine() {
    IntegerLiteralStateMachine integerLiteralStateMachine = new IntegerLiteralStateMachine();
    integerLiteralStateMachine.init();

    // Test input: 12345
    integerLiteralStateMachine.processInput('1');
    assertEquals("State 1", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput('2');
    assertEquals("State 1", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput('3');
    assertEquals("State 1", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput('5');
    assertEquals("State 1", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput(' ');
    assertEquals("State 2", integerLiteralStateMachine.getCurrentState().getName());

    assertTrue(integerLiteralStateMachine.isInAcceptState());
    integerLiteralStateMachine.reset();

    // Test input: 01
    integerLiteralStateMachine.processInput('0');
    assertEquals("State 3", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput('1');
    assertEquals("State 4", integerLiteralStateMachine.getCurrentState().getName());

    assertFalse(integerLiteralStateMachine.isInAcceptState());
    integerLiteralStateMachine.reset();

    // Test input: 1as
    integerLiteralStateMachine.processInput('1');
    assertEquals("State 1", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput('a');
    assertEquals("State 4", integerLiteralStateMachine.getCurrentState().getName());
    integerLiteralStateMachine.processInput('s');
    assertEquals("State 4", integerLiteralStateMachine.getCurrentState().getName());

    assertFalse(integerLiteralStateMachine.isInAcceptState());
  }

  @Test
  @DisplayName("Test StringLiteralStateMachine")
  public void testStringLiteralStateMachine() {
    StringLiteralStateMachine stringLiteralStateMachine = new StringLiteralStateMachine();
    stringLiteralStateMachine.init();

    // Test input: "Hallo!"
    stringLiteralStateMachine.processInput('"');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('H');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('a');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('l');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('l');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('o');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('!');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('"');
    assertEquals("State 3", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput(' ');
    assertEquals("State 4", stringLiteralStateMachine.getCurrentState().getName());

    assertTrue(stringLiteralStateMachine.isInAcceptState());
    stringLiteralStateMachine.reset();

    // Test input: "H\"i"
    stringLiteralStateMachine.processInput('"');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('H');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('\\');
    assertEquals("State 2", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('"');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('i');
    assertEquals("State 1", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('"');
    assertEquals("State 3", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput(' ');
    assertEquals("State 4", stringLiteralStateMachine.getCurrentState().getName());

    assertTrue(stringLiteralStateMachine.isInAcceptState());
    stringLiteralStateMachine.reset();

    // Test input: as"
    stringLiteralStateMachine.processInput('a');
    assertEquals("State 5", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('s');
    assertEquals("State 5", stringLiteralStateMachine.getCurrentState().getName());
    stringLiteralStateMachine.processInput('"');
    assertEquals("State 5", stringLiteralStateMachine.getCurrentState().getName());

    assertFalse(stringLiteralStateMachine.isInAcceptState());
    stringLiteralStateMachine.reset();

  }

  @Test
  @DisplayName("Test IdentifierStateMachine")
  public void testIdentifierStateMachine() {
    IdentifierStateMachine identifierStateMachine = new IdentifierStateMachine();
    identifierStateMachine.init();

    // Test input: varchar
    identifierStateMachine.processInput('v');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('a');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('r');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('c');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('h');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('a');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('r');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput(' ');
    assertEquals("State 2", identifierStateMachine.getCurrentState().getName());

    assertTrue(identifierStateMachine.isInAcceptState());
    identifierStateMachine.reset();

    // Test input: :Var
    identifierStateMachine.processInput(':');
    assertEquals("State 3", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('V');
    assertEquals("State 3", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('a');
    assertEquals("State 3", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('r');
    assertEquals("State 3", identifierStateMachine.getCurrentState().getName());

    assertFalse(identifierStateMachine.isInAcceptState());
    identifierStateMachine.reset();

    // Test input: Var091
    identifierStateMachine.processInput('V');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('a');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('r');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('0');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('9');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput('1');
    assertEquals("State 1", identifierStateMachine.getCurrentState().getName());
    identifierStateMachine.processInput(' ');
    assertEquals("State 2", identifierStateMachine.getCurrentState().getName());

    assertTrue(identifierStateMachine.isInAcceptState());
  }

  @Test
  @DisplayName("Test KeywordStateMachine")
  public void testKeywordStateMachine() {
    KeywordStateMachine keywordStateMachine1 = new KeywordStateMachine("for");
    keywordStateMachine1.init();

    // Test input: for
    keywordStateMachine1.processInput('f');
    assertEquals("State f", keywordStateMachine1.getCurrentState().getName());
    keywordStateMachine1.processInput('o');
    assertEquals("State o", keywordStateMachine1.getCurrentState().getName());
    keywordStateMachine1.processInput('r');
    assertEquals("State r", keywordStateMachine1.getCurrentState().getName());
    keywordStateMachine1.processInput(' ');
    assertEquals("State True", keywordStateMachine1.getCurrentState().getName());

    assertTrue(keywordStateMachine1.isInAcceptState());
    keywordStateMachine1.reset();

    // Test input: fora
    keywordStateMachine1.processInput('f');
    assertEquals("State f", keywordStateMachine1.getCurrentState().getName());
    keywordStateMachine1.processInput('o');
    assertEquals("State o", keywordStateMachine1.getCurrentState().getName());
    keywordStateMachine1.processInput('r');
    assertEquals("State r", keywordStateMachine1.getCurrentState().getName());
    keywordStateMachine1.processInput('a');
    assertEquals("State False", keywordStateMachine1.getCurrentState().getName());

    assertFalse(keywordStateMachine1.isInAcceptState());
    keywordStateMachine1.reset();

  }

}
