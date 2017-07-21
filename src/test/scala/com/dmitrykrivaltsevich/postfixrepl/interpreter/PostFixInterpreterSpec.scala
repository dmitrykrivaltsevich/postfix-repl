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
      {eval("(postfix 0 (1 2) 3 lt)") must_== "not enough numbers to lt"} // pending

    "gt" command:
      ${eval("(postfix 0 1 2 gt)") must_== "0"}
      ${eval("(postfix 0 2 1 gt)") must_== "1"}
      ${eval("(postfix 0 1 gt)") must_== "not enough numbers to gt"}
      ${eval("(postfix 0 gt)") must_== "not enough numbers to gt"}
      {eval("(postfix 0 (1 2) 3 gt)") must_== "not enough numbers to gt"} // pending

    "eq" command:
      ${eval("(postfix 0 1 2 eq)") must_== "0"}
      ${eval("(postfix 0 2 2 eq)") must_== "1"}
      ${eval("(postfix 0 1 eq)") must_== "not enough numbers to eq"}
      ${eval("(postfix 0 eq)") must_== "not enough numbers to eq"}
      {eval("(postfix 0 (1 2) 3 eq)") must_== "not enough numbers to eq"} // pending

    mixed commands:
      ${eval("(postfix 0 1057 888 sub 514 add)") must_== "683"}
      ${eval("(postfix 0 1 2 add 3 sub 4 mul)") must_== "0"}
      ${eval("(postfix 0 3 4 mul add)") must_== "not enough numbers to add"}
      ${eval("(postfix 0 8 3 sub 3 div 1 add)") must_== "2"}
      ${eval("(postfix 0 13 4 div 5 add 3 rem)") must_== "2"}
      ${eval("(postfix 0 0 1 lt 4 mul)") must_== "4"}
      ${eval("(postfix 0 0 1 gt 4 mul)") must_== "0"}
      ${eval("(postfix 0 0 0 eq)") must_== "1"}
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
