package com.dmitrykrivaltsevich.postfixrepl.interpreter

import scala.util.parsing.combinator.RegexParsers

object PostFixCommandParser extends RegexParsers {

  def program: Parser[(Int, List[PostFixCommand])] = "(postfix" ~> naturalNumber ~ commandList <~ ")" ^^ { result =>
    val ~(numberOfArguments, commands) = result
    (numberOfArguments, commands)
  }

  def naturalNumber: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def commandList: Parser[List[PostFixCommand]] = command ~ rep(" " ~> command) ^^ { result =>
    val ~(head, tail) = result
    head :: tail
  }

  def command: Parser[PostFixCommand] = numerical

  def numerical: Parser[NumberCommand] = """-?\d+""".r ^^ { result =>
    NumberCommand(result.toInt)
  } | failure("numerical value expected")

  def apply(input: String): Either[ParserFailure, (Int, List[PostFixCommand])] = parseAll(program, input) match {
    case Success(result, _) => Right(result)
    case NoSuccess(message, _) => Left(ParserFailure(message))
  }

}

case class ParserFailure(message: String)
