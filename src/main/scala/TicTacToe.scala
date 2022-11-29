import scala.io.StdIn.readLine

@main def hello: Unit = 
  println("== Tic-tac-toe Game ==")
  println("The first move is yours!")
  Board().play(userPositionSupplier, computerPositionSupplier)
  println(msg)


def computerPositionSupplier(board:Board): (Int, Int) = (0, 0)

def userPositionSupplier(board:Board): (Int, Int) = (0, 0)

def msg = "I was compiled by Scala 3. :)"
