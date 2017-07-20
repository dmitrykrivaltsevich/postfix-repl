package com.dmitrykrivaltsevich.postfixrepl.interpreter

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class PostFixInterpreterSpec extends Specification {

  def is: SpecStructure = s2"""

    ${eval("(postfix 0 1)") must_== "1"}
    ${eval("(postfix 0 1 2)") must_== "2"}

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
