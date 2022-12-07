import scala.util.Random
import scala.compiletime.ops.boolean

def randomMovementSelection(board: Board): (Int, Int) = Random.shuffle(board.availableChoices()).head

def dummyMovementSelection(board: Board): (Int, Int) = board.availableChoices().head

def boardHeuristic(board: Board, deep: Int): Float = {
    if (board.hasWinner() && board.winner() == BoardChoice.Player2) then
        +10f / deep
    else if (board.hasWinner() && board.winner() == BoardChoice.Player1) then
        -10f * deep
    else 
        0f
}

def minimax(board: Board, deep: Int, max: Boolean): Float = {
    if board.hasWinner() then
        boardHeuristic(board, deep)
    else if board.availableChoices().isEmpty then
        0f
    else if max then
        board.availableChoices()
             .map(c => (c, board.choice(c, BoardChoice.Player2)))
             .map(t => (t._1, minimax(t._2, deep + 1, false)))
             .maxBy(_._2)
             ._2
    else 
        board.availableChoices()
             .map(c => (c, board.choice(c, BoardChoice.Player1)))
             .map(t => (t._1, minimax(t._2, deep + 1, true)))
             .minBy(_._2)
             ._2
} 

def minimaxMovementSelection(board: Board): (Int, Int) = board.availableChoices()
                                                              .map(c => (c, minimax(board.choice(c, BoardChoice.Player2), 1, false)))
                                                              .maxBy(_._2)
                                                              ._1