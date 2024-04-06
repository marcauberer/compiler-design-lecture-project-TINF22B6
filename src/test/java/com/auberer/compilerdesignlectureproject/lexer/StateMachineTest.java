package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.State;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
