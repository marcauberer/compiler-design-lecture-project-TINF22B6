@echo off

java -jar lib/antlr-complete.jar -o src\main\java\com\auberer\compilerdesignlectureproject\antlr\gen -package com.auberer.compilerdesignlectureproject.antlr.gen -visitor -no-listener -Dlanguage=Java TInf.g4