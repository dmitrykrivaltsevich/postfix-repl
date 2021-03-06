package com.dmitrykrivaltsevich.postfixrepl.interpreter

import scala.annotation.tailrec
import scala.math.BigInt

class PostFixInterpreter(numberOfArguments: Int, args: List[Int]) {
  require(numberOfArguments == args.size) // TODO: move it into 'eval'

  @tailrec
  final def eval(commands: List[Command], stack: List[Command]): ProgramResult = {
    commands match {
      case Nil => resultFrom(stack)
      case head :: tail =>
        val stepResult = eval(head, stack)
        stepResult match {
          case StepSuccess(updatedStack, commandsToPrepend) => eval(commandsToPrepend ++ tail, updatedStack)
          case StepFailure(_, message) => ProgramFailure(message)
        }
    }
  }

  // TODO: reduce complexity
  // scalastyle:off
  private def eval(command: Command, stack: List[Command]): StepResult = command match {
    case nc: Numerical => StepSuccess(nc :: stack)

    case Add() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest => StepSuccess(Numerical(v2 + v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to add")
    }

    case Sub() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest => StepSuccess(Numerical(v2 - v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to sub")
    }

    case Mul() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest => StepSuccess(Numerical(v2 * v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to mul")
    }

    case Div() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest =>
        if (v1 == 0) StepFailure(stack, "divide by zero")
        else StepSuccess(Numerical(v2 / v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to div")
    }

    case Rem() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest =>
        if (v1 == 0) StepFailure(stack, "divide by zero")
        else StepSuccess(Numerical(v2 % v1) :: rest)
      case _ => StepFailure(stack, "not enough numbers to rem")
    }

    case Lt() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest =>
        if (v2 < v1) StepSuccess(Numerical(1) :: rest)
        else StepSuccess(Numerical(0) :: rest)
      case _ => StepFailure(stack, "not enough numbers to lt")
    }

    case Gt() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest =>
        if (v2 > v1) StepSuccess(Numerical(1) :: rest)
        else StepSuccess(Numerical(0) :: rest)
      case _ => StepFailure(stack, "not enough numbers to gt")
    }

    case Eq() => stack match {
      case Numerical(v1) :: Numerical(v2) :: rest =>
        if (v2.equals(v1)) StepSuccess(Numerical(1) :: rest)
        else StepSuccess(Numerical(0) :: rest)
      case _ => StepFailure(stack, "not enough numbers to eq")
    }

    case Pop() => stack match {
      case _ :: rest => StepSuccess(rest)
      case _ => StepFailure(stack, "stack is empty")
    }

    case Swap() => stack match {
      case c1 :: c2 :: rest => StepSuccess(c2 :: c1 :: rest)
      case _ => StepFailure(stack, "not enough commands to swap")
    }

    case Sel() => stack match {
      case c1 :: c2 :: Numerical(v) :: rest =>
        if (v == 0) StepSuccess(c1 :: rest)
        else StepSuccess(c2 :: rest)
      case _ => StepFailure(stack, "not enough values to sel")
    }

    case Nget() => stack match {
      case Numerical(v) :: rest =>
        if (v < 1) StepFailure(stack, s"index $v is too small")
        else if (v > rest.size) StepFailure(stack, s"index $v is too large")
        else {
          rest.drop(v.toInt - 1).head match {
            case n: Numerical => StepSuccess(n :: rest)
            case _ => StepFailure(stack, s"value at index $v is not a numeral")
          }
        }
      case _ => StepFailure(stack, "v_index is not a numeral")
    }

    case es: ExecutableSequence => StepSuccess(es :: stack)

    case Exec() => stack match {
      case ExecutableSequence(commands) :: rest => StepSuccess(rest, commands)
      case Nil => StepFailure(stack, "stack is empty")
      case _ => StepFailure(stack, "top stack value isn't an executable sequence")
    }

    case _ => StepFailure(stack, s"unknown command '$command'")
  }
  // scalastyle:on

  private def resultFrom(stack: List[Command]): ProgramResult =
    stack match {
      case Nil => ProgramFailure("empty stack")
      case Numerical(value) :: _ => ProgramSuccess(value)
      case _ => ProgramFailure("not a number on top of the stack")
    }

}

trait ProgramResult
case class ProgramSuccess(value: BigInt) extends ProgramResult
case class ProgramFailure(message: String) extends ProgramResult

trait StepResult
case class StepSuccess(stack: List[Command], commands: List[Command] = Nil) extends StepResult
case class StepFailure(stack: List[Command], message: String) extends StepResult
