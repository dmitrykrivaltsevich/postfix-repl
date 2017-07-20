package com.dmitrykrivaltsevich.postfixrepl.interpreter

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class PostFixInterpreterSpec extends Specification {

  def is: SpecStructure = s2"""

    "PostFix" interpreter should
      return a number from the top of the stack as program result $checkResultFromStack

  """

  private def checkResultFromStack = {
    val interpreter = new PostFixInterpreter(0, Nil)
    val stack = List.empty

    interpreter.eval(commands = List(NumberCommand(1)), stack) must beEqualTo(ProgramSuccess(1))
    interpreter.eval(commands = List(NumberCommand(1), NumberCommand(2)), stack) must beEqualTo(ProgramSuccess(2))
  }
}
