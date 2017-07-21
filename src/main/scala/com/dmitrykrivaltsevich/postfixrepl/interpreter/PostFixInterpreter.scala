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
          case StepSuccess(updatedStack) => eval(tail, updatedStack)
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
case class StepSuccess(stack: List[Command]) extends StepResult
case class StepFailure(stack: List[Command], message: String) extends StepResult
