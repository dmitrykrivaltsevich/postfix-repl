## Overview of the project
"PostFix REPL" is a simple REPL (read-eval-print-loop) for the "PostFix" language described in the book [Design 
Concepts in Programming Languages](https://www.amazon.com/Design-Concepts-Programming-Languages-Press/dp/0262201755).
 I haven't found any interpreters or compilers to play with described mini-language, so I decided to write my own.

## Overview of the PreFix language
PostFix is 'A Simple Stack Language' (see page 8 in the book).

PostFix language grammar (informally):
```
program             ::= (start-token natural-number command-list)
start-token         ::= postfix
natural-nuber       ::= Any nonnegative integer (0, 1, 2, etc.)
command-list        ::= command
    | command command-list
command             ::= numerical
    | command-token
    | executable-sequence
numerical           ::= Any integer numeral. E.g., 17, 0, -3, etc
command-token       ::= add
    | div
    | eq
    | exec
    | gt
    | lt
    | mul
    | nget
    | pop
    | rem
    | sel
    | sub
    | swap
executable-sequence := (command-list)
```

Sample programs in PostFix language:
```
(postfix 0 4 7 sub)
(postfix 2 add 2 div)
(postfix 4 4 nget 5 nget mul mul swap 4 nget mul add add)
(postfix 1 ((3 nget swap exec) (2 mul swap exec) swap) (5 sub) swap exec exec)
```

## Users guideline
Use: `sbt run` command to run interactive interpreter. Type `exit` or `:q` to exit the interpreter.

## Contributors guideline

Step 0: Fork it!

Step 1: Build it.

In order to build "PostFix REPL" from sources you'll need to have:
1. Scala 2.12.2 SDK
2. Sbt 0.13.15

Compile and run tests: `sbt clean test`. Make sure that build works and all tests pass before you start adding new 
features or fixing bugs.

Step 2: [Submit an issue](https://github.com/dmitrykrivaltsevich/postfix-repl/issues). Good issue answers two 
questions: `what` and `why`.

Step 3: Work in a separate branch, prepend every commit by issue number like `#10 your commit message`.

Step 4: Make a Pull Request from the branch in your fork repository to the master branch in this repository.

Done.
