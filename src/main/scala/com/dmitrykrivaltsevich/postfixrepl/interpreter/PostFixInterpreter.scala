package com.dmitrykrivaltsevich.postfixrepl.interpreter

import scala.annotation.tailrec

class PostFixInterpreter(numberOfArguments: Int, args: List[Int]) {
  require(numberOfArguments == args.size) // TODO: move it into 'eval'

  @tailrec
  final def eval(commands: List[PostFixCommand], stack: List[PostFixCommand]): ProgramResult = {
    commands match {
      case Nil => resultFrom(stack)
      case (head: NumberCommand) :: tail =>
        val updatedStack = head +: stack
        eval(tail, updatedStack)
      case _ => ProgramFailure(s"unknown command '${commands.head}'")
    }
  }

  private def resultFrom(stack: List[PostFixCommand]): ProgramResult =
    stack match {
      case Nil => ProgramFailure("empty stack")
      case NumberCommand(value) :: _ => ProgramSuccess(value)
      case _ => ProgramFailure("not a number on top of the stack")
    }

}

trait ProgramResult
case class ProgramSuccess(value: Int) extends ProgramResult
case class ProgramFailure(message: String) extends ProgramResult

trait StepResult
case class StepSuccess(stack: List[PostFixCommand]) extends StepResult
case class StepFailure(stack: List[PostFixCommand], error: String) extends StepResult
