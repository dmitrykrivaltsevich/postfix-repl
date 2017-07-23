package com.dmitrykrivaltsevich.postfixrepl.interpreter

trait Command

case class Numerical(number: BigInt) extends Command
case class Add() extends Command
case class Sub() extends Command
case class Mul() extends Command
case class Div() extends Command
case class Rem() extends Command
case class Lt() extends Command
case class Gt() extends Command
case class Eq() extends Command
case class Pop() extends Command
case class Swap() extends Command
case class Sel() extends Command
case class Nget() extends Command
case class ExecutableSequence(commands: List[Command]) extends Command
