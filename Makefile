regenerate-parser-tinf:
	java -jar lib/antlr-complete.jar -o src/main/java/com/auberer/compilerdesignlectureproject/antlr/gen -package com.auberer.compilerdesignlectureproject.antlr.gen -visitor -no-listener -Dlanguage=Java TInf.g4
regenerate-parser-example:
	java -jar lib/antlr-complete.jar -o example -package com.example.antlr.gen -visitor -no-listener -Dlanguage=Java Example.g4
