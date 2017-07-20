package com.dmitrykrivaltsevich.postfixrepl.interpreter

class PostFixRepl {

  private var isRunning = true

  def loop(in: => String, out: String => Unit, err: String => Unit): Unit =
    while (isRunning) {
      in match {
        case "exit" | ":q" =>
          isRunning = false
          out("Exiting...")

        case input => PostFixCommandParser(input) match {
          case Right((_, parsedCommands)) =>
            // note: we do not pass arguments yet
            val interpreter = new PostFixInterpreter(0, args = Nil)
            val stack = List.empty
            interpreter.eval(parsedCommands, stack) match {
              case ProgramSuccess(value) => out(value.toString)
              case ProgramFailure(message) => err(message)
            }

          case Left(ParserFailure(message)) => err(message)
        }
      }
    }
}
