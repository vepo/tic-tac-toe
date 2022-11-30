import munit.FunSuite

// https://scalameta.org/munit/docs/getting-started.html
class BoardSuite extends FunSuite {
  test("The empty board has no winner") {
    assert(!Board().hasWinner())
  }
}
