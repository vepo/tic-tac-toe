import scala.io.StdIn.readChar
import scala.io.StdIn.readInt
import scala.io.StdIn.readLine
import java.io.EOFException

@main def hello: Unit = 
  try 
    println("== Tic-tac-toe Game ==")
    println("")
    while
      val finalBoard = Board().play(userPositionSupplier, computerPositionSupplier(readAISelection()), BoardChoice.Player1)
      if finalBoard.hasWinner() then
        println(s"We have a WINNER!!! winner=${finalBoard.winner()}")
      else
        println("Sorry... No winner this time!")
      finalBoard.show("Final Board")
      playAgain()
    do ()
  catch 
    case ie: InterruptedException => println("It's done! Thanks!")

def playAgain(): Boolean = {
  println("Do you want to play again? [Y/n]")
  try
    readChar() match
      case 'Y' | 'y' => true
      case 'N' | 'n' => false
      case _ => {
        println("I did not understand what you mean. Please try again..")
        playAgain()
      }
  catch
    // Just 'enter', use the default value 'Y'
    case soe: StringIndexOutOfBoundsException => true
    case eof: EOFException => true 
}

def selectAI(id: Int): (Board => (Int, Int)) = id match
    case 1 => dummyMovementSelection
    case 2 => randomMovementSelection
    case 3 => minimaxMovementSelection

def readAISelection(): (Board => (Int, Int)) = {
  println("We have the following AI implemented!")
  println(" (1) First option")
  println(" (2) Random Choice")
  println(" (3) Minimax Algorithm")
  print("Please select one of them: ")  
  try
    val aiOption = readInt()
    if aiOption <= 0 || aiOption >= 4 then
      println("Invalid choice! Please select (1, 2 or 3). Try again...")
      readAISelection()
    else
      selectAI(aiOption)
  catch
    case nfe: NumberFormatException => {
      println("I could not understand your option. Please, try again...")
      readAISelection()
    }
    case eof: EOFException => {
      println("I could not identify your choice. Please, try again...")
      readAISelection()
    }
}

def computerPositionSupplier(ai: (Board => (Int, Int))): (Board => (Int, Int)) = (board: Board) => { 
  val choice = ai.apply(board)
  println(s"The AI selected (${choice._1}, ${choice._2})!")
  choice
}

def userPositionSupplier(board: Board): (Int, Int) = {
  board.show()
  println()
  print("[YOU] Insert your choice (type \"x,y\"): ")
  val coordinates = readLine().split(",")
  if coordinates.length == 2 then
    try
      val choice = (coordinates(0).trim().toInt, coordinates(1).trim().toInt)
      if board.availableChoices().contains(choice) then 
        choice
      else
        println("Choice not available! Try again...")
        userPositionSupplier(board)
    catch
      case nfe: NumberFormatException => {
        println("I do not understood your choice. Try again...")
        userPositionSupplier(board)
      }
  else
    println("Invalid input! Use the following pattern: \"0, 0\"")
    userPositionSupplier(board)
}