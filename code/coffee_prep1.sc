//> using scala "3.1.2"

type IO[A] = A // should be an actual IO-like type

extension (value: Double)
  def within(lower: Double, upper: Double): Boolean =
    value >= lower && value <= upper

case class Water(amount: Double)
case class Grounds(amount: Double)

def pour1(water: Water, grounds: Grounds): IO[Unit] = ???

case class TastyRatio private (water: Water, grounds: Grounds)

object TastyRatio:
  def make(water: Water, grounds: Grounds): Option[TastyRatio] = 
    if (water.amount / grounds.amount).within(14, 15)
    then Some(TastyRatio(water, grounds))
    else None

def pour2(tastyRatio: TastyRatio): IO[Unit] = ???

println(TastyRatio.make(Water(15), Grounds(1)))
println(TastyRatio.make(Water(16), Grounds(1)))
