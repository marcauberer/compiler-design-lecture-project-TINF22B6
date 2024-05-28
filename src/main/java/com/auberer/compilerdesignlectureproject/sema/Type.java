package com.auberer.compilerdesignlectureproject.sema;

import lombok.Data;

@Data
public class Type {
  private SuperType superType;
  private String subType;

  public Type(SuperType superType) {
    this.superType = superType;
  }

  public Type(SuperType superType, String subType) {
    this.superType = superType;
    this.subType = subType;
  }

  public boolean is(SuperType superType) {
    return this.superType == superType;
  }

  public boolean isOneOf(SuperType... superTypes) {
    for (SuperType superType : superTypes) {
      if (this.superType == superType) {
        return true;
      }
    }
    return false;
  }

  public String toString() {
    return superType.toString();
  }
}
