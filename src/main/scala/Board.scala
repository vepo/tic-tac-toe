import java.awt.Choice

enum BoardChoice:
  case Player1, Player2, None

class Board(val positions: Array[BoardChoice] = Array.fill(9) { BoardChoice.None }):

  def play(player1: Board => (Int, Int),
           player2: Board => (Int, Int),
           turn: BoardChoice): Board = 
        (this.hasWinner(), this.canFinish(turn), turn) match
            case (true, _, _) => this
            case (_, false, _) => this
            case (_, _, BoardChoice.Player1) => {
              this.show()
              this.choice(player1.apply(this), BoardChoice.Player1).play(player1, player2, BoardChoice.Player2)
            }
            case (_, _, BoardChoice.Player2) => {
              this.choice(player2.apply(this), BoardChoice.Player2).play(player1, player2, BoardChoice.Player1)
            }
            case (_, _, BoardChoice.None) => {
              println("Error")
              this
            }

  def choice(position: (Int, Int), player: BoardChoice): Board = Board(this.positions.updated[BoardChoice](arrayPosition(position), player))

  def playerSymbol(player: BoardChoice): String = {
    player match
      case BoardChoice.Player1 => "X"
      case BoardChoice.Player2 => "0"
      case BoardChoice.None => "_"
    
  }

  def canFinish(turn: BoardChoice): Boolean = hasWinner() || (!this.availableChoices()
                                                                   .isEmpty && 
                                                              this.availableChoices()
                                                                  .map(c => this.choice(c, turn))
                                                                  .exists(b => b.canFinish(if turn == BoardChoice.Player1 then BoardChoice.Player1 else BoardChoice.Player1)))

  def hasWinner(): Boolean =
   hasWinner(0, 1, 2) || hasWinner(3, 4, 5) || hasWinner(6, 7, 8) || 
   hasWinner(0, 3, 6) || hasWinner(1, 4, 7) || hasWinner(2, 5, 8) ||
   hasWinner(0, 4, 8) || hasWinner(2, 4, 6)
  
  def winner(): BoardChoice =
    if hasWinner(0, 1, 2) then
      this.positions(0)
    else if hasWinner(3, 4, 5) then 
      this.positions(3)
    else if hasWinner(6, 7, 8) then 
      this.positions(6)
    else if hasWinner(0, 3, 6) then 
      this.positions(0)
    else if hasWinner(1, 4, 7) then
      this.positions(1)
    else if hasWinner(2, 5, 8) then
      this.positions(2)
    else if hasWinner(0, 4, 5) then
      this.positions(0)
    else if hasWinner(2, 4, 6) then
      this.positions(2)
    else
      BoardChoice.None

  def availableChoices(): List[(Int, Int)] = this.positions.zipWithIndex.filter(_._1 == BoardChoice.None).map(p => (p._2 / 3, p._2 % 3)).toList
  

  def hasWinner(pos1: Int, pos2: Int, pos3: Int): Boolean = 
    this.positions(pos1) != BoardChoice.None && this.positions(pos1) == this.positions(pos2) && this.positions(pos1) == this.positions(pos3)

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
