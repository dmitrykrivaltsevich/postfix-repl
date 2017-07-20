package com.dmitrykrivaltsevich.postfixrepl.interpreter

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class PostFixInterpreterSpec extends Specification {

  def is: SpecStructure = s2"""

    ${eval("(postfix 0 1)") must_== "1"}
    ${eval("(postfix 0 1 2)") must_== "2"}
    ${eval("(postfix 0 1 2 add)") must_== "3"}
    ${eval("(postfix 0 1 2 3 add)") must_== "5"}
    ${eval("(postfix 0 add)") must_== "not enough numbers to add"}
    ${eval("(postfix 0 1 add)") must_== "not enough numbers to add"}

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
