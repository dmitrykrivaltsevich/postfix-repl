package com.dmitrykrivaltsevich.postfixrepl.interpreter

class PostFixInterpreter(numberOfArguments: Int, args: List[Int]) {
  require(numberOfArguments == args.size) // TODO: move it into 'eval'

  //  def eval(commands: Stack[PostFixCommand]): ProgramResult = ???

  //  private def eval(command: PostFixCommand, stack: Stack[PostFixCommand]): StepResult = command match {
  //    case nc: NumberCommand => StepSuccess(stack.push(nc))
  //    case _ => StepFailure(stack, s"unknown command '$command'")
  //  }

}

trait ProgramResult
case class ProgramSuccess(value: Int) extends ProgramResult
case class ProgramFailure(message: String) extends ProgramResult

trait StepResult
case class StepSuccess(stack: List[PostFixCommand]) extends StepResult
case class StepFailure(stack: List[PostFixCommand], error: String) extends StepResult
