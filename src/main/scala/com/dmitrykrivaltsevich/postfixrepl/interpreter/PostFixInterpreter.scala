package com.dmitrykrivaltsevich.postfixrepl.interpreter

import scala.annotation.tailrec
import scala.math.BigInt

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
    }
  }

  // TODO: reduce complexity
  // scalastyle:off
  private def eval(command: PostFixCommand, stack: List[PostFixCommand]): StepResult = command match {
    case nc: NumericalCommand => StepSuccess(nc :: stack)

    case AddCommand() => stack match {
      case NumericalCommand(v1) :: NumericalCommand(v2) :: rest => StepSuccess(NumericalCommand(v2 + v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to add")
    }

    case SubCommand() => stack match {
      case NumericalCommand(v1) :: NumericalCommand(v2) :: rest => StepSuccess(NumericalCommand(v2 - v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to sub")
    }

    case MulCommand() => stack match {
      case NumericalCommand(v1) :: NumericalCommand(v2) :: rest => StepSuccess(NumericalCommand(v2 * v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to mul")
    }

    case DivCommand() => stack match {
      case NumericalCommand(v1) :: NumericalCommand(v2) :: rest =>
        if (v1 == 0) StepFailure(stack, "divide by zero")
        else StepSuccess(NumericalCommand(v2 / v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to div")
    }

    case RemCommand() => stack match {
      case NumericalCommand(v1) :: NumericalCommand(v2) :: rest =>
        if (v1 == 0) StepFailure(stack, "divide by zero")
        else StepSuccess(NumericalCommand(v2 % v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to rem")
    }

    case _ => StepFailure(stack, s"unknown command '$command'")
  }
  // scalastyle:on

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
