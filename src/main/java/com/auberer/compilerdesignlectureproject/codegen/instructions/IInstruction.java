package com.auberer.compilerdesignlectureproject.codegen.instructions;

import com.auberer.compilerdesignlectureproject.codegen.IDumpable;
import com.auberer.compilerdesignlectureproject.codegen.IRunnable;

public interface IInstruction extends IRunnable, IDumpable {
  void trace(StringBuilder sb);
}
