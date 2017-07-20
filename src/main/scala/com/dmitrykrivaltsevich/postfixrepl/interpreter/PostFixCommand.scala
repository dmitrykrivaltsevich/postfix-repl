package com.dmitrykrivaltsevich.postfixrepl.interpreter

trait PostFixCommand

case class NumericalCommand(number: BigInt) extends PostFixCommand
case object AddCommand extends PostFixCommand
