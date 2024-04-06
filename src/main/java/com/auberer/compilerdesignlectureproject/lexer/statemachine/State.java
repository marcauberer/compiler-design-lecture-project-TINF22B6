package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import lombok.Data;

@Data
public class State {
  private String name;
  private boolean isStartState = false;
  private boolean isAcceptState = false;

  public State(String name) {
    this.name = name;
  }

  public String getDotCode() {
    return name + " [label=\"" + name + "\"]";
  }
}
