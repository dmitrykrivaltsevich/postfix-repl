package com.dmitrykrivaltsevich.postfixrepl.interpreter

class PostFixRepl {

  private var isRunning = true
  private val interpreter = new PostFixInterpreter(0, Nil)

  def loop(in: => String, out: String => Unit, err: String => Unit): Unit =
    while (isRunning)
      in match {
        case "exit" | ":q" =>
          isRunning = false
          out("Exiting...")

        case source =>
          val commands = parse(source)
          interpreter.eval(commands, List.empty) match {
            case ProgramSuccess(value) => out(value.toString)
            case ProgramFailure(message) => err(message)
          }
      }

  // TODO: ad-hoc implementation, replace by parser
  private def parse(str: String): List[PostFixCommand] =
    str.drop(1) // left  '('
      .dropRight(1) // right ')'
      .split(" ") // get tokens
      .drop(2) // remove 'postfix' and number of arguments
      .map(token => NumberCommand(token.toInt))
      .toList
}
