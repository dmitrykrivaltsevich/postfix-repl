package com.dmitrykrivaltsevich.postfixrepl.interpreter

class PostFixRepl {

  def loop(in: => String, out: String => Unit): Unit =
    while (true) {
      out(in)
    }

}