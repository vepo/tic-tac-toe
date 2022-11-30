import scala.io.StdIn.readLine

@main def hello: Unit = 
  println("== Tic-tac-toe Game ==")
  println("The first move is yours!")
  val finalBoard = Board().play(userPositionSupplier, computerPositionSupplier, Choice.Player1)
  if finalBoard.hasWinner() then
    println(s"We have a WINNER!!! winner=${finalBoard.winner()}")


def computerPositionSupplier(board:Board): (Int, Int) = board.availableChoices()(0)

def userPositionSupplier(board:Board): (Int, Int) = {  
  println()
  println("Insert your choice")
  val coordinates = readLine().split(",")
  if coordinates.length == 2 then
    try
      val choice = (coordinates(0).trim().toInt, coordinates(1).trim().toInt)
      if board.availableChoices().contains(choice) then 
        choice
      else
        println("Choice not available!")
        userPositionSupplier(board)
    catch
      case nfe: NumberFormatException => {
        println("I do not understood your choice. Try again...")
        userPositionSupplier(board)
      }
  else
    println("Invalid input!")
    userPositionSupplier(board)
}