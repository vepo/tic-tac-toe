import java.awt.Choice

/**
 * 
 */
enum BoardChoice:
  case Player1, Player2, None


def swapPlayer(p1: BoardChoice): BoardChoice = if p1 == BoardChoice.Player1 then BoardChoice.Player2 else BoardChoice.Player1

val WinnerPositions = Array((0, 1, 2), 
                            (3, 4, 5), 
                            (6, 7, 8), 
                            (0, 3, 6), 
                            (1, 4, 7), 
                            (2, 5, 8), 
                            (0, 4, 8), 
                            (2, 4, 6))

/**
 * Represents the current state of the board.
 */
class Board(val positions: Array[BoardChoice] = Array.fill(9) { BoardChoice.None }):
  
  def play(player1: Board => (Int, Int),
           player2: Board => (Int, Int),
           turn: BoardChoice): Board = 
        (this.hasWinner(), this.canFinish(turn), turn) match
            case (true, _,     _) => this
            case (_,    false, _) => this
            case (_,    _,     BoardChoice.Player1) => this.choice(player1.apply(this), BoardChoice.Player1).play(player1, player2, BoardChoice.Player2)
            case (_,    _,     BoardChoice.Player2) => this.choice(player2.apply(this), BoardChoice.Player2).play(player1, player2, BoardChoice.Player1)
            case (_,    _,     BoardChoice.None) => this

  def choice(position: (Int, Int), player: BoardChoice): Board = Board(this.positions.updated[BoardChoice](arrayPosition(position), player))

  def playerSymbol(player: BoardChoice): String = {
    player match
      case BoardChoice.Player1 => "X"
      case BoardChoice.Player2 => "0"
      case BoardChoice.None    => "_"
    
  }

  /**
   * @return true if the current player still won or false 
   *         if there is no possible way to finish the match.
   */
  def canFinish(turn: BoardChoice): Boolean = hasWinner() || (!this.availableChoices()
                                                                   .isEmpty && 
                                                              this.availableChoices()
                                                                  .map(c => this.choice(c, turn))
                                                                  .exists(b => b.canFinish(swapPlayer(turn))))

  /**
   * @return true if the board has a winner, false otherwise
   */
  def hasWinner(): Boolean = WinnerPositions.exists(p => this.hasWinner(p))

  def winner(): BoardChoice = WinnerPositions.filter(p => this.hasWinner(p))
                                             .map(p => this.positions(p._1))
                                             .headOption
                                             .getOrElse(BoardChoice.None)

  def availableChoices(): List[(Int, Int)] = this.positions.zipWithIndex
                                                           .filter(_._1 == BoardChoice.None)
                                                           .map(p => (p._2 / 3, p._2 % 3))
                                                           .toList

  def hasWinner(pos: (Int, Int, Int)): Boolean = 
    this.positions(pos._1) != BoardChoice.None && this.positions(pos._1) == this.positions(pos._2) && this.positions(pos._1) == this.positions(pos._3)

//+---------+
//| _  _  0 |
//| _  _  _ |
//| _  _  _ |
//+---------+
  def show(header: String="Current Board") = {
    println(s"""
${header}
+---------+
| ${playerSymbol(this.positions(0))}  ${playerSymbol(this.positions(1))}  ${playerSymbol(this.positions(2))} |
| ${playerSymbol(this.positions(3))}  ${playerSymbol(this.positions(4))}  ${playerSymbol(this.positions(5))} |
| ${playerSymbol(this.positions(6))}  ${playerSymbol(this.positions(7))}  ${playerSymbol(this.positions(8))} |
+---------+""")
  }

  private def arrayPosition(pos: (Int, Int)): Int = (pos._1 * 3) + pos._2
end Board
