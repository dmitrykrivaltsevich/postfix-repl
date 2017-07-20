package com.dmitrykrivaltsevich.postfixrepl.interpreter

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class PostFixInterpreterSpec extends Specification {

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

    mixed commands:
      ${eval("(postfix 0 1057 888 sub 514 add)") must_== "683"}
      ${eval("(postfix 0 1 2 add 3 sub 4 mul)") must_== "0"}
  """

  private def eval(input: String) = {
    val Right((_, commands)) = PostFixCommandParser(input)

    val interpreter = new PostFixInterpreter(0, Nil)
    interpreter.eval(commands, Nil) match {
      case ProgramSuccess(value) => value.toString()
      case ProgramFailure(message) => message
    }
  }
}
