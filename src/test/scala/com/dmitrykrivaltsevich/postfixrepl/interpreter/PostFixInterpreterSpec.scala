package com.dmitrykrivaltsevich.postfixrepl.interpreter

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class PostFixInterpreterSpec extends Specification {

  // scalastyle:off
  def is: SpecStructure = s2"""

    numerical command:
      ${eval("(postfix 0 1)") must_== "1"}
      ${eval("(postfix 0 1 2)") must_== "2"}

    "add" command:
      ${eval("(postfix 0 1 2 add)") must_== "3"}
      ${eval("(postfix 0 1 2 3 add)") must_== "5"}
      ${eval("(postfix 0 1 -1 add)") must_== "0"}
      ${eval("(postfix 0 35 125 add 354 add)") must_== "514"}
      ${eval("(postfix 0 add)") must_== "not enough numbers to add"}
      ${eval("(postfix 0 1 add)") must_== "not enough numbers to add"}

    "sub" command:
      ${eval("(postfix 0 3 4 sub)") must_== "-1"}
      ${eval("(postfix 0 3 4 2 sub)") must_== "2"}
      ${eval("(postfix 0 sub)") must_== "not enough numbers to sub"}
      ${eval("(postfix 0 1 sub)") must_== "not enough numbers to sub"}

    "mul" command:
      ${eval("(postfix 0 2 2 mul)") must_== "4"}
      ${eval("(postfix 0 2 1 mul)") must_== "2"}
      ${eval("(postfix 0 1 2 mul)") must_== "2"}
      ${eval("(postfix 0 -1 2 mul)") must_== "-2"}
      ${eval("(postfix 0 -1 -3 mul)") must_== "3"}
      ${eval("(postfix 0 mul)") must_== "not enough numbers to mul"}
      ${eval("(postfix 0 1 mul)") must_== "not enough numbers to mul"}

    "div" command:
      ${eval("(postfix 0 4 2 div)") must_== "2"}
      ${eval("(postfix 0 3 1 div)") must_== "3"}
      ${eval("(postfix 0 1 3 div)") must_== "0"}
      ${eval("(postfix 0 0 2 div)") must_== "0"}
      ${eval("(postfix 0 2 0 div)") must_== "divide by zero"}
      ${eval("(postfix 0 2 div)") must_== "not enough numbers to div"}
      ${eval("(postfix 0 div)") must_== "not enough numbers to div"}

    "rem" command:
      ${eval("(postfix 0 4 2 rem)") must_== "0"}
      ${eval("(postfix 0 3 1 rem)") must_== "0"}
      ${eval("(postfix 0 1 3 rem)") must_== "1"}
      ${eval("(postfix 0 5 2 rem)") must_== "1"}
      ${eval("(postfix 0 17 3 rem)") must_== "2"}
      ${eval("(postfix 0 0 2 rem)") must_== "0"}
      ${eval("(postfix 0 2 0 rem)") must_== "divide by zero"}
      ${eval("(postfix 0 2 rem)") must_== "not enough numbers to rem"}
      ${eval("(postfix 0 rem)") must_== "not enough numbers to rem"}

    "lt" command:
      ${eval("(postfix 0 1 2 lt)") must_== "1"}
      ${eval("(postfix 0 2 1 lt)") must_== "0"}
      ${eval("(postfix 0 1 lt)") must_== "not enough numbers to lt"}
      ${eval("(postfix 0 lt)") must_== "not enough numbers to lt"}
      ${eval("(postfix 0 (1 2) 3 lt)") must_== "not enough numbers to lt"}

    "gt" command:
      ${eval("(postfix 0 1 2 gt)") must_== "0"}
      ${eval("(postfix 0 2 1 gt)") must_== "1"}
      ${eval("(postfix 0 1 gt)") must_== "not enough numbers to gt"}
      ${eval("(postfix 0 gt)") must_== "not enough numbers to gt"}
      ${eval("(postfix 0 (1 2) 3 gt)") must_== "not enough numbers to gt"}

    "eq" command:
      ${eval("(postfix 0 1 2 eq)") must_== "0"}
      ${eval("(postfix 0 2 2 eq)") must_== "1"}
      ${eval("(postfix 0 1 eq)") must_== "not enough numbers to eq"}
      ${eval("(postfix 0 eq)") must_== "not enough numbers to eq"}
      ${eval("(postfix 0 (1 2) 3 eq)") must_== "not enough numbers to eq"}

    "pop" command:
      ${eval("(postfix 0 1 2 pop)") must_== "1"}
      ${eval("(postfix 0 1 2 3 pop)") must_== "2"}
      ${eval("(postfix 0 pop)") must_== "stack is empty"}
      ${eval("(postfix 0 1 pop pop)") must_== "stack is empty"}

    "swap" command:
      ${eval("(postfix 0 1 2 swap)") must_== "1"}
      ${eval("(postfix 0 1 swap)") must_== "not enough commands to swap"}
      ${eval("(postfix 0 swap)") must_== "not enough commands to swap"}

    "sel" command:
      ${eval("(postfix 0 0 0 9 sel)") must_== "9"}
      ${eval("(postfix 0 1 8 9 sel)") must_== "8"}
      ${eval("(postfix 0 8 9 sel)") must_== "not enough values to sel"}
      ${eval("(postfix 0 9 sel)") must_== "not enough values to sel"}
      ${eval("(postfix 0 sel)") must_== "not enough values to sel"}
      ${eval("(postfix 0 0 8 (5 4 add) sel)") must_== "not a number on top of the stack"}
      ${eval("(postfix 0 (1) 8 9 sel)") must_== "not enough values to sel"}

    "nget" command:
      ${eval("(postfix 0 5 4 1 nget)") must_== "4"}
      ${eval("(postfix 0 5 4 2 nget)") must_== "5"}
      ${eval("(postfix 0 5 4 3 nget)") must_== "index 3 is too large"}
      ${eval("(postfix 0 5 4 0 nget)") must_== "index 0 is too small"}
      ${eval("(postfix 0 5 4 (1) nget)") must_== "v_index is not a numeral"}
      ${eval("(postfix 0 3 (2 mul) 1 nget)") must_== "value at index 1 is not a numeral"}

    "exec" command:
      ${eval("(postfix 0 (1) exec)") must_== "1"}
      ${eval("(postfix 0 (1 2) exec)") must_== "2"}
      ${eval("(postfix 0 (1 2 add) exec)") must_== "3"}
      ${eval("(postfix 0 exec)") must_== "stack is empty"}
      ${eval("(postfix 0 1 exec)") must_== "top stack values isn't an executable sequence"}

    mixed commands:
      ${eval("(postfix 0 1057 888 sub 514 add)") must_== "683"}
      ${eval("(postfix 0 1 2 add 3 sub 4 mul)") must_== "0"}
      ${eval("(postfix 0 3 4 mul add)") must_== "not enough numbers to add"}
      ${eval("(postfix 0 8 3 sub 3 div 1 add)") must_== "2"}
      ${eval("(postfix 0 13 4 div 5 add 3 rem)") must_== "2"}
      ${eval("(postfix 0 0 1 lt 4 mul)") must_== "4"}
      ${eval("(postfix 0 0 1 gt 4 mul)") must_== "0"}
      ${eval("(postfix 0 0 0 eq)") must_== "1"}
      ${eval("(postfix 0 5 1 nget mul)") must_== "25"}
      ${eval("(postfix 0 2 5 4 3 4 nget 5 nget mul mul swap 4 nget mul add add)") must_== "25"}

  """
  // scalastyle:on

  private def eval(input: String) = {
    val Right((_, commands)) = PostFixCommandParser(input)

    val interpreter = new PostFixInterpreter(0, Nil)
    interpreter.eval(commands, Nil) match {
      case ProgramSuccess(value) => value.toString()
      case ProgramFailure(message) => message
    }
  }
}
