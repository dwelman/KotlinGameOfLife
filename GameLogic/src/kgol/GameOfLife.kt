package kgol

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


val ALIVE_VIZ = "O"
val DEAD_VIZ = "."

fun main(args: Array<String>) {
    var gameMap = createEmptyMap(25, 25)
    gameMap = randomlyPopulateMap(gameMap)
    var vizMap = createEmptyVizMap(gameMap[0].size,gameMap.size)

    vizMap = updateVizMap(gameMap)

    while(true){
        Thread.sleep(200)
        gameMap = applyRules(gameMap)
        vizMap = updateVizMap(gameMap)
    }

}

fun randomlyPopulateMap(map: Array<Array<Boolean>>) :  Array<Array<Boolean>> {
    val xSize: Int = map[0].size
    val ySize: Int = map.size

    val returnMap = createEmptyMap(xSize, ySize)

    for (y in 0 until ySize) {
        var row = arrayOf<Boolean>()
        for (x in 0 until xSize) {
            val randomValue = List(1) { Random.nextInt(0, 5) }
            if(randomValue[0] == 1)
                returnMap[x][y] = true
        }
    }

    return returnMap
}

fun createEmptyVizMap(xSize: Int, ySize: Int) : Array<Array<String>>{
    var cells = arrayOf<Array<String>>()

    for (y in 0 until ySize) {
        var row = arrayOf<String>()
        for (x in 0 until xSize) {
            row += DEAD_VIZ
        }
        cells += row
    }
    return cells
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
                aliveNeighbourCount > 3 && map[y][x] -> false
                aliveNeighbourCount == 3 && !map[y][x] -> true
                else -> false
            }

        }
    }

    return returnMap
}

fun updateVizMap(dataMap: Array<Array<Boolean>>) :  Array<Array<String>>{
    val xSize: Int = dataMap[0].size
    val ySize: Int = dataMap.size

    val vizMap = createEmptyVizMap(xSize, ySize)

    for (y in 0 until ySize) {
        var row = arrayOf<String>()
        for (x in 0 until xSize) {
            vizMap[x][y] = if(dataMap[x][y]) {
                ALIVE_VIZ
            }
            else{
                DEAD_VIZ
            }
        }
    }

    displayVizMap(vizMap)
    return vizMap
}

fun displayVizMap(vizMap: Array<Array<String>>){
    val xSize: Int = vizMap[0].size
    val ySize: Int = vizMap.size

    var currentLine:String

    for (x in 0 until xSize) {
        currentLine = ""
        var row = arrayOf<String>()
        for (y in 0 until ySize) {
            currentLine+=vizMap[x][y]
        }
        println(currentLine)
    }
    println()
}