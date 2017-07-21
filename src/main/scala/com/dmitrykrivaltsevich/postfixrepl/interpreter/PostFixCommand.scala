package com.dmitrykrivaltsevich.postfixrepl.interpreter

trait PostFixCommand

case class NumericalCommand(number: BigInt) extends PostFixCommand
case class AddCommand() extends PostFixCommand
case class SubCommand() extends PostFixCommand
case class MulCommand() extends PostFixCommand
case class DivCommand() extends PostFixCommand
case class RemCommand() extends PostFixCommand
case class LtCommand() extends PostFixCommand
