import munit.FunSuite

// https://scalameta.org/munit/docs/getting-started.html
class BoardSuite extends FunSuite {
  test("The empty board has no winner") {
    assert(!Board().hasWinner())
  }
  test(""" The board has winner
+---------+
| X  X  X |
| _  _  _ |
| _  _  _ |
+---------+
""") {
    assert(Board(Array(BoardChoice.Player1, BoardChoice.Player1, BoardChoice.Player1, 
                       BoardChoice.None,    BoardChoice.None,    BoardChoice.None, 
                       BoardChoice.None,    BoardChoice.None,    BoardChoice.None)).hasWinner())
  }
  test(""" The board has winner
+---------+
| _  _  _ |
| X  X  X |
| _  _  _ |
+---------+
""") {
    assert(Board(Array(BoardChoice.Player1, BoardChoice.None,    BoardChoice.None, 
                       BoardChoice.Player1, BoardChoice.None,    BoardChoice.None, 
                       BoardChoice.Player1, BoardChoice.None,    BoardChoice.None)).hasWinner())
  }
  test(""" The board has winner
+---------+
| _  _  _ |
| _  _  _ |
| X  X  X |
+---------+
""") {
    assert(Board(Array(BoardChoice.None, BoardChoice.Player1, BoardChoice.None, 
                       BoardChoice.None, BoardChoice.Player1, BoardChoice.None, 
                       BoardChoice.None, BoardChoice.Player1, BoardChoice.None)).hasWinner())
  }
  test(""" The board has winner
+---------+
| X  _  _ |
| X  _  _ |
| X  _  _ |
+---------+
""") {
    assert(Board(Array(BoardChoice.Player1, BoardChoice.None, BoardChoice.None, 
                       BoardChoice.Player1, BoardChoice.None, BoardChoice.None, 
                       BoardChoice.Player1, BoardChoice.None, BoardChoice.None)).hasWinner())
  }
  test(""" The board has winner
+---------+
| _  X  _ |
| _  X  _ |
| _  X  _ |
+---------+
""") {
    assert(Board(Array(BoardChoice.None, BoardChoice.Player1, BoardChoice.None, 
                       BoardChoice.None, BoardChoice.Player1, BoardChoice.None, 
                       BoardChoice.None, BoardChoice.Player1, BoardChoice.None)).hasWinner())
  }
  test(""" The board has winner
+---------+
| _  _  X |
| _  _  X |
| _  _  X |
+---------+
""") {
    assert(Board(Array(BoardChoice.None,    BoardChoice.None, BoardChoice.Player1, 
                       BoardChoice.None,    BoardChoice.None, BoardChoice.Player1, 
                       BoardChoice.None,    BoardChoice.None, BoardChoice.Player1)).hasWinner())
  }
  test(""" The board has winner
+---------+
| X  _  _ |
| _  X  _ |
| _  _  X |
+---------+
""") {
    assert(Board(Array(BoardChoice.Player1, BoardChoice.None,    BoardChoice.None, 
                       BoardChoice.None,    BoardChoice.Player1, BoardChoice.None, 
                       BoardChoice.None,    BoardChoice.None,    BoardChoice.Player1)).hasWinner())
  }
  test(""" The board has winner
+---------+
| _  _  X |
| _  X  _ |
| X  _  _ |
+---------+
""") {
    assert(Board(Array(BoardChoice.None,    BoardChoice.None,    BoardChoice.Player1, 
                       BoardChoice.None,    BoardChoice.Player1, BoardChoice.None, 
                       BoardChoice.Player1, BoardChoice.None,    BoardChoice.None)).hasWinner())
                       
  }
}
