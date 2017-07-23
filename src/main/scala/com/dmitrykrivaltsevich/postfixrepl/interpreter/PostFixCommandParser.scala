package com.dmitrykrivaltsevich.postfixrepl.interpreter

import scala.util.parsing.combinator.RegexParsers

object PostFixCommandParser extends RegexParsers {

  def program: Parser[(Int, List[Command])] = "(postfix" ~> naturalNumber ~ commandList <~ ")" ^^ { result =>
    val ~(numberOfArguments, commands) = result
    (numberOfArguments, commands)
  }

  def naturalNumber: Parser[Int] = """\d+""".r ^^ (_.toInt) withFailureMessage "number of arguments expected"

  def commandList: Parser[List[Command]] = command ~ rep(command) ^^ { result =>
    val ~(head, tail) = result
    head :: tail
  }

  def command: Parser[Command] = numerical |
    add | sub | mul | div |
    rem | lt | gt | eq | pop |
    swap | sel | nget | exec | executableSequence withFailureMessage "command or numeral expected"

  def numerical: Parser[Numerical] = """-?\d+""".r ^^ { result =>
    Numerical(BigInt(result))
  } withFailureMessage "numerical value expected"

  def add: Parser[Add] = "add" ^^ (_ => Add())

  def sub: Parser[Sub] = "sub" ^^ (_ => Sub())

  def mul: Parser[Mul] = "mul" ^^ (_ => Mul())

  def div: Parser[Div] = "div" ^^ (_ => Div())

  def rem: Parser[Rem] = "rem" ^^ (_ => Rem())

  def lt: Parser[Lt] = "lt" ^^ (_ => Lt())

  def gt: Parser[Gt] = "gt" ^^ (_ => Gt())

  def eq: Parser[Eq] = "eq" ^^ (_ => Eq())

  def pop: Parser[Pop] = "pop" ^^ (_ => Pop())

  def swap: Parser[Swap] = "swap" ^^ (_ => Swap())

  def sel: Parser[Sel] = "sel" ^^ (_ => Sel())

  def nget: Parser[Nget] = "nget" ^^ (_ => Nget())

  def exec: Parser[Exec] = "exec" ^^ (_ => Exec())

  def executableSequence: Parser[ExecutableSequence] = "(" ~> commandList <~ ")" ^^ { result =>
    ExecutableSequence(result)
  }

  def apply(input: String): Either[ParserFailure, (Int, List[Command])] = parseAll(program, input) match {
    case Success(result, _) => Right(result)
    case NoSuccess(message, _) => Left(ParserFailure(message))
  }

}

case class ParserFailure(message: String)
