package kgol

import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.CoreMatchers.*

class GameOfLifeTests {
    @Test
    fun createGameMap() {
        val gameMap = createEmptyMap(50, 50)
        assertThat(gameMap.size, `is`(50))
        assertThat(gameMap[0].size, `is`(50))
        assertThat(gameMap[0][0], `is`(false))
        assertThat(gameMap[24][13], `is`(false))
        assertThat(gameMap[49][49], `is`(false))
    }

    @Test
    fun testLiveForever(){
        val gameMap = createEmptyMap(10, 10)

        gameMap[0][0] = true
        gameMap[0][1] = true
        gameMap[1][0] = true
        gameMap[1][1] = true

        var newMap = applyRules(gameMap)
        for (i in 1..100) {
            newMap = applyRules(newMap)
            assertThat(newMap[0][0], `is`(true))
            assertThat(newMap[0][1], `is`(true))
            assertThat(newMap[1][0], `is`(true))
            assertThat(newMap[1][1], `is`(true))
        }
    }

    //1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
    @Test
    fun testUnderpopulationRule() {
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

        gameMap[7][7] = true
        gameMap[8][8] = true

        val newMap = applyRules(gameMap)
        //Check that the old map has not been modified
        assertThat(gameMap[0][0], `is`(true))
        assertThat(gameMap[0][1], `is`(true))
        assertThat(gameMap[1][0], `is`(true))
        assertThat(gameMap[1][1], `is`(true))

        assertThat(gameMap[5][5], `is`(true))
        assertThat(gameMap[5][6], `is`(true))
        assertThat(gameMap[5][7], `is`(true))
        assertThat(gameMap[6][5], `is`(true))

        assertThat(gameMap[7][7], `is`(true))
        assertThat(gameMap[8][8], `is`(true))

        //Check that underpopulation rule has been applied
        assertThat(newMap[0][0], `is`(true))
        assertThat(newMap[0][1], `is`(true))
        assertThat(newMap[1][0], `is`(true))
        assertThat(newMap[1][1], `is`(true))

        assertThat(newMap[5][5], `is`(true))
        assertThat(newMap[5][6], `is`(true))
        assertThat(newMap[5][7], `is`(false))
        assertThat(newMap[6][5], `is`(true))

        assertThat(newMap[7][7], `is`(false))
        assertThat(newMap[8][8], `is`(false))
    }

    //2. Any live cell with two or three live neighbours lives on to the next generation.
    @Test
    fun testLivesOnRule() {
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
        //Check that the old map has not been modified
        assertThat(gameMap[0][0], `is`(true))
        assertThat(gameMap[0][1], `is`(true))
        assertThat(gameMap[1][0], `is`(true))
        assertThat(gameMap[1][1], `is`(true))

        for (i in 1..10) {
            newMap = applyRules(newMap)
            assertThat(newMap[0][0], `is`(true))
            assertThat(newMap[0][1], `is`(true))
            assertThat(newMap[1][0], `is`(true))
            assertThat(newMap[1][1], `is`(true))

        }
    }

    //3. Any live cell with more than three live neighbours dies, as if by overpopulation.
    @Test
    fun testOverPopulationRule(){
        val gameMap = createEmptyMap(10, 10)

        gameMap[0][0] = true
        gameMap[0][1] = true
        gameMap[1][0] = true
        gameMap[1][1] = true

        gameMap[3][3] = true
        gameMap[4][2] = true
        gameMap[4][3] = true
        gameMap[5][2] = true
        gameMap[5][3] = true

        var newMap = applyRules(gameMap)

        assertThat(newMap[0][0], `is`(true))
        assertThat(newMap[0][1], `is`(true))
        assertThat(newMap[1][0], `is`(true))
        assertThat(newMap[1][1], `is`(true))

        assertThat(newMap[3][3], `is`(true))
        assertThat(newMap[4][2], `is`(false))
        assertThat(newMap[4][3], `is`(false))
        assertThat(newMap[5][2], `is`(true))
        assertThat(newMap[5][3], `is`(true))
    }

    //4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
    @Test
    fun testReproductionRule(){
        val gameMap = createEmptyMap(10, 10)

        gameMap[1][5] = false
        gameMap[1][6] = true
        gameMap[2][5] = true
        gameMap[2][6] = true

        var newMap = applyRules(gameMap)

        assertThat(newMap[1][5], `is`(true))
        assertThat(newMap[1][6], `is`(true))
        assertThat(newMap[2][5], `is`(true))
        assertThat(newMap[2][6], `is`(true))

    }

    @Test
    fun testEmptyDisplay(){
        val vizMap = createEmptyVizMap(50,50)

        assertThat(vizMap.size, `is`(50))
        assertThat(vizMap[0].size, `is`(50))

        for (y in 0 until vizMap.size) {
            var row = arrayOf<String>()
            for (x in 0 until vizMap[0].size) {
                assertThat(vizMap[x][y], `is`(DEAD_VIZ))
            }
        }
    }

    @Test
    fun testDisplay(){
        //initial cells spawn with "." representing an alive cell and " " representing a dead cell
        val gameMap = createEmptyMap(10, 10)

        gameMap[1][1] = true
        gameMap[1][2] = true
        gameMap[2][1] = true
        gameMap[2][2] = true

        gameMap[3][0] = true
        gameMap[4][0] = true

        gameMap[6][0] = true
        gameMap[6][1] = true
        gameMap[7][1] = true

        var updatedVizMap = updateVizMap(gameMap)

        assertThat(updatedVizMap[1][1], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[1][2], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[2][1], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[2][2], `is`(ALIVE_VIZ))

        assertThat(updatedVizMap[3][0], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[4][0], `is`(ALIVE_VIZ))

        assertThat(updatedVizMap[6][0], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[6][1], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[7][1], `is`(ALIVE_VIZ))


        var newMap = applyRules(gameMap)
        updatedVizMap = updateVizMap(newMap)
        assertThat(updatedVizMap[1][1], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[1][2], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[2][1], `is`(DEAD_VIZ))
        assertThat(updatedVizMap[2][2], `is`(ALIVE_VIZ))

        assertThat(updatedVizMap[3][0], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[4][0], `is`(DEAD_VIZ))

        assertThat(updatedVizMap[6][0], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[6][1], `is`(ALIVE_VIZ))
        assertThat(updatedVizMap[7][1], `is`(ALIVE_VIZ))

    }
}