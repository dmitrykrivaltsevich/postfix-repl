package com.dmitrykrivaltsevich.postfixrepl.interpreter

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class PostFixInterpreterSpec extends Specification {

  def is: SpecStructure = s2"""

    "PostFix" interpreter should
      return a number from the top of the stack as program result $checkResultFromStack
      return failure when stack is empty at the end of the program $checkFailureOnEmptyStack

  """

  private def checkResultFromStack = {
    val interpreter = new PostFixInterpreter(0, Nil)

    interpreter.eval(
      commands = List(NumericalCommand(1), NumericalCommand(2)),
      stack = List.empty
    ) must beEqualTo(ProgramSuccess(2))
  }

  private def checkFailureOnEmptyStack = {
    val interpreter = new PostFixInterpreter(0, Nil)

    interpreter.eval(commands = List.empty, stack = List.empty) must beEqualTo(ProgramFailure("empty stack"))
  }
}
