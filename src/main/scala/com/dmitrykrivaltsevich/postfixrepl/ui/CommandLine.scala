package com.dmitrykrivaltsevich.postfixrepl.ui

import com.dmitrykrivaltsevich.postfixrepl.interpreter.PostFixRepl

import scala.io.StdIn

object CommandLine extends App {

  println(
    """
      |This is PostFix shell.
      |Type in expressions to have them evaluated.
    """.stripMargin
  )

  val repl = new PostFixRepl
  repl.loop(in, out)

  private def in: String = {
    print("> ")
    StdIn.readLine()
  }

  private def out(msg: String): Unit =
    println(s"$msg\n")

}