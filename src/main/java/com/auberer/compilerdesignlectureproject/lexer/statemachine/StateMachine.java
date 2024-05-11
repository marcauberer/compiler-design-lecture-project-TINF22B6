package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public abstract class StateMachine implements IStateMachine {
  private final List<State> states = new ArrayList<>();
  private final List<Transition> transitions = new ArrayList<>();
  @Getter
  private State currentState;
  @Getter
  private String acceptedInput = "";
  @Setter
  @Getter
  private boolean traceEnabled = false;

  /**
   * Add a state to the state machine.
   *
   * @param state The state to add
   */
  public void addState(State state) {
    states.add(state);
  }

  /**
   * Add a transition from one state to another with a given input.
   *
   * @param from The state to transition from
   * @param to The state to transition to
   * @param input The input that triggers the transition
   */
  @Override
  public void addCharTransition(State from, State to, char input) {
    transitions.add(new CharTransition(from, to, input));
  }

  /**
   * Add a transition from one state to another with a given range.
   *
   * @param from The state to transition from
   * @param to The state to transition to
   * @param range The range that triggers the transition
   */
  @Override
  public void addRangeTransition(State from, State to, Range range) throws IllegalArgumentException {
    if (!range.isValid())
      throw new IllegalArgumentException("Invalid range: " + range);
    transitions.add(new RangeTransition(from, to, range));
  }

  /**
   * Add an else transition from one state to another.
   * This transition is taken if no other transition is possible.
   *
   * @param from The state to transition from
   * @param to The state to transition to
   */
  @Override
  public void addElseTransition(State from, State to) {
    transitions.add(new ElseTransition(from, to));
  }

  /**
   * Process an input character and transition to the next state.
   *
   * @param input The input character
   */
  public void processInput(char input) throws IllegalStateException {
    if (currentState == null)
      throw new IllegalStateException("State machine not initialized");

    if (traceEnabled)
      System.out.println("Current state: " + currentState.getName());

    // Search for a transition that matches the input
    Transition elseTransition = null;
    for (Transition transition : transitions) {
      // Skip if is no outgoing transition from the current state
      if (!transition.getFromState().equals(currentState))
        continue;

      // Check if this is an else transition and store it for later
      if (transition instanceof ElseTransition) {
        elseTransition = transition;
        continue;
      }

      // Check if the transition matches the input
      if (transition.matches(input)) {
        acceptedInput += input;
        transitionToState(transition.getToState(), input);
        return;
      }
    }

    // If no transition was found, but an else transition is present, perform the else transition
    if (elseTransition != null) {
      acceptedInput += input;
      transitionToState(elseTransition.getToState(), input);
      return;
    }

    throw new IllegalStateException("No transition found for input '" + input + "' in state '" + currentState.getName() + "'");
  }

  /**
   * Reset the state machine to the start state.
   */
  public void reset() {
    currentState = states.stream()
        .filter(State::isStartState)
        .findFirst()
        .orElse(null);
    acceptedInput = "";
  }

  /**
   * Check if the state machine is in an accept state.
   *
   * @return True if the state machine is in an accept state
   */
  public boolean isInAcceptState() {
    return currentState.isAcceptState();
  }

  /**
   * Get the Graphviz DOT code for the state machine.
   *
   * @return The DOT code
   */
  public String getDotCode() {
    StringBuilder dotCode = new StringBuilder("digraph G {\n");
    for (State state : states) {
      dotCode.append(state.getDotCode()).append("\n");
    }
    for (Transition transition : transitions) {
      dotCode.append(transition.getDotCode()).append("\n");
    }
    dotCode.append("}");
    return dotCode.toString();
  }

  /**
   * Dump the Graphviz DOT code for the state machine to the console.
   */
  public void dumpDotCode() {
    System.out.println("Dumping DOT code for state machine '" + this.getClass().getSimpleName() + "':");
    System.out.println(getDotCode());
    System.out.println("Paste this code at https://dreampuf.github.io/GraphvizOnline to visualize the state machine.");
  }

  private void transitionToState(State toState, char input) {
    if (traceEnabled)
      System.out.println("Transition: " + currentState.getName() + " -> " + toState.getName() + " via " + input);
    currentState = toState;
  }
}
