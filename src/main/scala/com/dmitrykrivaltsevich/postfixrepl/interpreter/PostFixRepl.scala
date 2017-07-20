package com.dmitrykrivaltsevich.postfixrepl.interpreter

class PostFixRepl {

  private var isRunning = true

  def loop(in: => String, out: String => Unit): Unit =
    while (isRunning)
      in match {
        case "exit" | ":q" => {
          isRunning = false
          out("Exiting...")
        }
        case source => out(source)
      }

}
