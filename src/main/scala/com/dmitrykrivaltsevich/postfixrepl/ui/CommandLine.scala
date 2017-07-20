package com.dmitrykrivaltsevich.postfixrepl.ui

import com.dmitrykrivaltsevich.postfixrepl.interpreter.PostFixRepl

import scala.Console.{GREEN, RESET, RED}
import scala.annotation.tailrec
import scala.io.StdIn

object CommandLine extends App {

  out(
    """
      |This is PostFix shell.
      |Type in expressions to have them evaluated.
    """.stripMargin
  )

  val repl = new PostFixRepl
  repl.loop(in, out, err)

  @tailrec
  private def in: String = {
    Console.print(s"$RESET$GREEN>$RESET ")
    StdIn.readLine() match {
      case "" => in
      case str => str
    }
  }

  private def out(msg: String): Unit =
    Console.print(s"$msg\n\n")

  private def err(msg: String): Unit =
    out(s"$RESET${RED}error: $msg$RESET")

}
