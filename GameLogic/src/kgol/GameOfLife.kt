package kgol

import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val gameMap = createEmptyMap(10, 10)
    //Any live cell with fewer than two live neighbours dies, as if by underpopulation.

    //This cluster should live forever in theory
    gameMap[0][0] = true
    gameMap[0][1] = true
    gameMap[1][0] = true
    gameMap[1][1] = true

    gameMap[5][5] = true
    gameMap[5][6] = true
    gameMap[5][7] = true
    gameMap[6][5] = true

    var newMap = applyRules(gameMap)
    newMap = applyRules(newMap)
}

fun createEmptyMap(xSize: Int, ySize: Int) : Array<Array<Boolean>> {
    var cells = arrayOf<Array<Boolean>>()

    for (y in 0 until ySize) {
        var row = arrayOf<Boolean>()
        for (x in 0 until xSize) {
            row += false
        }
        cells += row
    }
    return cells
}

//Conway's Game of Life has 4 rules, all implemented as a pure function below:
//1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
//2. Any live cell with two or three live neighbours lives on to the next generation.
//3. Any live cell with more than three live neighbours dies, as if by overpopulation.
//4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

fun applyRules(map: Array<Array<Boolean>>) :  Array<Array<Boolean>> {
    val xSize: Int = map[0].size
    val ySize: Int = map.size
    val returnMap = createEmptyMap(xSize, ySize)
    var aliveNeighbourCount: Int

    //Iterate and perform
    for (y in 0 until ySize) {
        for (x in 0 until xSize) {
            //Current cell
            aliveNeighbourCount = 0

            //Get neighbours
            for (j in max(0, y-1)..min(y+1, ySize - 1)) {
                for (i in max(0, x-1)..min(x+1, xSize - 1)) {
                    if (i != x || j != y) {
                        //This is a neighbour
                        if (map[j][i]) {
                            aliveNeighbourCount++
                        }
                    }
                }
            }

            returnMap[y][x] = when {
                aliveNeighbourCount  < 2 && map[y][x] -> false
                aliveNeighbourCount in 2..3 && map[y][x] -> true
                else -> false
            }

        }
    }

    return returnMap
}