@echo off

java -jar lib/antlr-complete.jar -o example -package com.example.antlr.gen -visitor -no-listener -Dlanguage=Java Example.g4