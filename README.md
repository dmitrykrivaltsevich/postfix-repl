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
    | sub
    | mul
    | div
    | rem
    | lt
    | gt
    | eq
    | pop
    | swap
    | sel
    | nget
    | exec
executable-sequence := (command-list)
```

Every "PostFix" program starts with `(postfix` keyword followed by non-negative number which represents number of 
arguments to the program with an arbitrary list of commands. Sample programs in PostFix language:
```
(postfix 0 4 7 sub)
(postfix 2 add 2 div)
(postfix 4 4 nget 5 nget mul mul swap 4 nget mul add add)
(postfix 1 ((3 nget swap exec) (2 mul swap exec) swap) (5 sub) swap exec exec)
```

Commands:
- `N`. Push the numeral N onto the stack.
- `add` (addition). Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the 
stack and push the result of `v2 + v1` onto the stack. If there are fewer than two values on the stack or the two top 
values aren't both numerals, signal an error.
- `sub` Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the stack and push the 
result of `v2 - v1` onto the stack. If there are fewer than two values on the stack or the two top values aren't both 
numerals, signal an error.
- `mul` (multiplication). Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the 
stack and push the result of `v2 * v1` onto the stack. If there are fewer than two values on the stack or the two top 
values aren't both numerals, signal an error.
- `div` (integer division). Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the
stack and push the result of `v2 / v1` onto the stack. If there are fewer than two values on the stack or the two top 
values aren't both numerals, signal an error. Signal an error if `v1` is zero.
- `rem` (reminder of integer division). Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two 
values off the stack and push the reminder of the result of `v2 / v1` onto the stack. If there are fewer than two 
values on the stack or the two top values aren't both numerals, signal an error. Signal an error if `v1` is zero.
- `lt`. Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the stack.
If `v2 < v1`, then push `1` (a true value) on the stack, otherwise push a `0` (false). If there are fewer then two 
values on the stack or the top two values aren't both numerals, signal an error.
- `gt`. Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the stack.
If `v2 > v1`, then push `1` (a true value) on the stack, otherwise push a `0` (false). If there are fewer then two 
values on the stack or the top two values aren't both numerals, signal an error.
- `eq`. Call the top stack value `v1` and next top-to-stack value `v2`. Pop these two values off the stack.
If `v2 = v1`, then push `1` (a true value) on the stack, otherwise push a `0` (false). If there are fewer then two 
values on the stack or the top two values aren't both numerals, signal an error.
- `pop`. Pop the top element off the stack and discard it. Signal an error if the stack is empty.
- `swap`. Swap the top two elements of the stack. Signal an error if the sack has fewer than two values.
- `sel`. Call the top three stack values (from top down) `v1`, `v2`, and `v3`. Pop these three values off the stack.
If `v3` is the numeral `0`, push `v1` onto the stack; if `v3` is a nonzero numeral, push `v2` onto the stack. Signal an
error if the stack does not contain three values, or if `v3` is not a numeral.
- `nget`. Call the top stack value `v_index` and the remaining stack values (from top down) `v1`, `v2`, ..., `vn`. Pop 
`v_index` off the stack. If `v_index` is a numeral `i` such that `1 <= i <= n` and `vi` is a numeral, push `vi` onto 
the stack. Signal an error if the stack does not contain at least one value, if `v_index` is not a numeral, if `i` is 
not in the range `[1..n]`, or if `vi` is not a numeral.
- `exec`. Pop the executable sequence from the top of the stack, and prepend its component commands onto the sequence 
of currently executing commands. Signal an error of the stack is empty or the top stack value isn't an executable 
sequence.
- `(C1 ... Cn)`. Push the _executable sequence_ `(C1 ... Cn)` as a single value onto the stack. Executable sequences
are used in conjunction with `exec`.

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
