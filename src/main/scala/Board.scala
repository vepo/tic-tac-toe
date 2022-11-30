import java.awt.Choice

enum Choice:
  case Player1, Player2, None

class Board(val positions: Array[Choice] = Array.fill(9) { Choice.None }):

  def play(player1: Board => (Int, Int),
           player2: Board => (Int, Int),
           turn: Choice): Board = 
        (this.hasWinner(), turn) match
            case (true, _) => this
            case (_, Choice.Player1) => {
              println("User playing....")
              this.show()
              Board(this.positions.updated[Choice](arrayPosition(player1.apply(this)), Choice.Player1)).play(player1, player2, Choice.Player2)
            }
            case (_, Choice.Player2) => {
              println("Computer playing...")
              this.show()
              Board(this.positions.updated[Choice](arrayPosition(player2.apply(this)), Choice.Player2)).play(player1, player2, Choice.Player1)
            }
            case (_, Choice.None) => {
              println("Error")
              this
            }
  def playerSymbol(player: Choice): String = {
    player match
      case Choice.Player1 => "X"
      case Choice.Player2 => "0"
      case Choice.None => "_"
    
  }

  def hasWinner(): Boolean =
   hasWinner(0, 1, 2) || hasWinner(3, 4, 5) || hasWinner(6, 7, 8) || 
   hasWinner(0, 3, 6) || hasWinner(1, 4, 7) || hasWinner(2, 5, 8) ||
   hasWinner(0, 4, 5) || hasWinner(2, 4, 6)
  
  def winner(): Choice =
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
      Choice.None

  def availableChoices(): List[(Int, Int)] = this.positions.zipWithIndex.filter(_._1 == Choice.None).map(p => (p._2 / 3, p._2 % 3)).toList
  

  def hasWinner(pos1: Int, pos2: Int, pos3: Int): Boolean = 
    this.positions(pos1) != Choice.None && this.positions(pos1) == this.positions(pos2) && this.positions(pos1) == this.positions(pos3)

//+---------+
//| _  _  0 |
//| _  _  _ |
//| _  _  _ |
//+---------+
  def show() = {
    println(s"""

Current Board
+---------+
| ${playerSymbol(this.positions(0))}  ${playerSymbol(this.positions(1))}  ${playerSymbol(this.positions(2))} |
| ${playerSymbol(this.positions(3))}  ${playerSymbol(this.positions(4))}  ${playerSymbol(this.positions(5))} |
| ${playerSymbol(this.positions(6))}  ${playerSymbol(this.positions(7))}  ${playerSymbol(this.positions(8))} |
+---------+""")
  }

  private def arrayPosition(pos: (Int, Int)): Int = (pos._1 * 3) + pos._2
end Board
