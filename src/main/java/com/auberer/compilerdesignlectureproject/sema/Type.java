package com.auberer.compilerdesignlectureproject.sema;

import lombok.Data;

@Data
public class Type {
  private SuperType superType;

  public Type(SuperType superType) {
    this.superType = superType;
  }
}
