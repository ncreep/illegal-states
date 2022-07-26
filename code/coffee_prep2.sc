//> using scala "3.1.2"

import scala.concurrent.duration._

case class IO[A](value: A): // should be an actual IO-like type
  def flatMap[B](f: A => IO[B]): IO[B] = f(value)
  def map[B](f: A => B): IO[B] = IO(f(value))

trait BrewingActions1:
  def grindCoffee: IO[Unit]
  def heatWater: IO[Unit]
  def foldFilter: IO[Unit]
  def placeFilter: IO[Unit]
  def rinseFilter: IO[Unit]
  def addGrounds: IO[Unit]

def brewingPrep1(brewer: BrewingActions1): IO[Unit] =
  for
    _ <- brewer.heatWater
    _ <- brewer.grindCoffee
    _ <- brewer.foldFilter
    _ <- brewer.placeFilter
    _ <- brewer.rinseFilter
    _ <- brewer.addGrounds
  yield ()

trait BrewingActions2[
  CoffeeGround,
  WaterHeated, 
  FilterFolded,
  FilterPlaced,
  FilterRinsed,
  GroundsAdded]:
    def grindCoffee: IO[CoffeeGround]
    def heatWater: IO[WaterHeated]
    def foldFilter: IO[FilterFolded]
    def placeFilter: IO[FilterPlaced]
    def rinseFilter: IO[FilterRinsed]
    def addGrounds: IO[GroundsAdded]
  
trait BrewingActions3[
  CoffeeGround,
  WaterHeated, 
  FilterFolded,
  FilterPlaced,
  FilterRinsed,
  GroundsAdded]:
    def grindCoffee: IO[CoffeeGround]
    def heatWater: IO[WaterHeated]
    def foldFilter: IO[FilterFolded]
    def placeFilter(
      filterFolded: FilterFolded): IO[FilterPlaced]
    def rinseFilter(
      filterPlaced: FilterPlaced,
      waterHeated: WaterHeated): IO[FilterRinsed]
    def addGrounds(
      coffeeGround: CoffeeGround,
      filterRinsed: FilterRinsed): IO[GroundsAdded]

def brewingPrep2[
  CoffeeGround,
  WaterHeated, 
  FilterFolded,
  FilterPlaced,
  FilterRinsed,
  GroundsAdded]
  (brewer: BrewingActions3[
    CoffeeGround, WaterHeated, FilterFolded, 
    FilterPlaced, FilterRinsed, GroundsAdded]): IO[(WaterHeated, GroundsAdded)] =
  for
    waterHeated <- brewer.heatWater
    coffeeGround <- brewer.grindCoffee
    filterFolded <- brewer.foldFilter
    filterPlaced <- brewer.placeFilter(filterFolded)
    filterRinsed <- brewer.rinseFilter(
                      filterPlaced, waterHeated)
    groundsAdded <- brewer.addGrounds(
                      coffeeGround, filterRinsed)
  yield (waterHeated, groundsAdded)
