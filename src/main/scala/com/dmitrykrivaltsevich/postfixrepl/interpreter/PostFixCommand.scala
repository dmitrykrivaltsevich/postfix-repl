package com.dmitrykrivaltsevich.postfixrepl.interpreter

trait PostFixCommand

case class NumericalCommand(number: BigInt) extends PostFixCommand
case class AddCommand() extends PostFixCommand
