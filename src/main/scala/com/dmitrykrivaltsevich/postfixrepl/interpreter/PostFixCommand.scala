package com.dmitrykrivaltsevich.postfixrepl.interpreter

trait PostFixCommand

case class NumberCommand(number: Int) extends PostFixCommand
