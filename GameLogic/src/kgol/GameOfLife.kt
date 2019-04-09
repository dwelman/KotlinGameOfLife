package kgol

class GameMap(val xSize: Int, val ySize: Int) {
    var cells = arrayOf<Array<Boolean>>()

    init {
        for (y in 0 until ySize) {
            var row = arrayOf<Boolean>()
            for (x in 0 until xSize) {
                row += false
            }
            cells += row
        }
    }
}