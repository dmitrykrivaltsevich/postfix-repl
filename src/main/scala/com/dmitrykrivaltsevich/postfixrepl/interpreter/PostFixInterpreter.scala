package com.dmitrykrivaltsevich.postfixrepl.interpreter

import scala.annotation.tailrec

class PostFixInterpreter(numberOfArguments: Int, args: List[Int]) {
  require(numberOfArguments == args.size) // TODO: move it into 'eval'

  @tailrec
  final def eval(commands: List[PostFixCommand], stack: List[PostFixCommand]): ProgramResult = {
    commands match {
      case Nil => resultFrom(stack)
      case head :: tail =>
        val stepResult = eval(head, stack)
        stepResult match {
          case StepSuccess(updatedStack) => eval(tail, updatedStack)
          case StepFailure(_, message) => ProgramFailure(message)
        }
      case _ => ProgramFailure(s"unknown command '${commands.head}'")
    }
  }

  private def eval(command: PostFixCommand, stack: List[PostFixCommand]): StepResult = command match {
    case nc: NumericalCommand => StepSuccess(nc :: stack)
    case AddCommand() => stack match {
      case NumericalCommand(v1) :: NumericalCommand(v2) :: rest => StepSuccess(NumericalCommand(v2 + v1) :: rest)
      case _ => StepFailure(stack, "not enoug numbers to add")
    }
    case _ => StepFailure(stack, s"unknown command '$command'")
  }

  private def resultFrom(stack: List[PostFixCommand]): ProgramResult =
    stack match {
      case Nil => ProgramFailure("empty stack")
      case NumericalCommand(value) :: _ => ProgramSuccess(value)
      case _ => ProgramFailure("not a number on top of the stack")
    }

}

trait ProgramResult
case class ProgramSuccess(value: BigInt) extends ProgramResult
case class ProgramFailure(message: String) extends ProgramResult

trait StepResult
case class StepSuccess(stack: List[PostFixCommand]) extends StepResult
case class StepFailure(stack: List[PostFixCommand], message: String) extends StepResult
