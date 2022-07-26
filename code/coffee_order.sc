//> using scala "3.1.2"

type IO[A] = A // should be an actual IO-like type

enum Milk:
  case Whole, 
       Skim,
       SoyJuice, // see https://twitter.com/thelewisblack/status/1233472832576851968
       Almond
       
enum Cream:
  case Heavy,
       HalfAndHalf,
       Soy
       
enum Gelato:
  case Vanilla,
       FiorDiLatte
       
enum Whiskey:
  case Tullamore,
       Jameson,
       Bushmills
  
// cappuccino
case class CoffeeOrder1(milk: Milk)

// cappuccino + espresso
case class CoffeeOrder2(milk: Option[Milk])

case class CoffeeOrder3(
  drinkType: DrinkType1,
  milk: Option[Milk])
                        
enum DrinkType1:
  case Cappuccino,
       Espresso,
       Latte
  
case class CoffeeOrder4(
  drinkType: DrinkType2,
  milk: Option[Milk],
  gelato: Option[Gelato],
  cream: Option[Cream],
  whiskey: Option[Whiskey])

enum DrinkType2:
  case Cappuccino,
       Espresso,
       Latte,
       Affogato,
       IrishCoffee

 def prepareIrishCoffee(cream: Cream, 
                        whiskey: Whiskey): IO[Unit] = ???

def prepareCoffeeOrder1(order: CoffeeOrder4): IO[Unit] = 
  import DrinkType2._
  
  order.drinkType match 
    case Cappuccino => ???
    case Espresso => ???
    case Latte => ???
    case Affogato => ???
    case IrishCoffee =>
      // SAFE: Irish coffee ALWAYS has cream and whiskey
      prepareIrishCoffee(
        order.cream.get,
        order.whiskey.get)

def prepareCoffeeOrder2(order: CoffeeOrder4): IO[Unit] = 
  import DrinkType2._
  
  order.drinkType match 
    case Cappuccino => ???
    case Espresso => ???
    case Latte => ???
    case Affogato => ???
    case IrishCoffee =>
      // SAFE: Irish coffee ALWAYS has cream and whiskey
      if order.cream.isEmpty 
      then throw IllegalStateException("No cream")
      if order.whiskey.isEmpty
      then throw IllegalStateException("No whiskey")
        
      prepareIrishCoffee(
        order.cream.get,
        order.whiskey.get)
    
enum CoffeeOrder5:
  case Cappuccino(milk: Milk)
  case Espresso
  case Latte(milk: Milk)
  case Affogato(gelato: Gelato)
  case IrishCoffee(cream: Cream, whiskey: Whiskey)
  
def prepareCoffeeOrder3(order: CoffeeOrder5): IO[Unit] = 
  import CoffeeOrder5._
  
  order match 
    case cappuccino: Cappuccino => ???
    case Espresso => ???
    case latte: Latte => ???
    case affogato: Affogato => ???
    case IrishCoffee(cream, whiskey) =>
      prepareIrishCoffee(cream, whiskey)
