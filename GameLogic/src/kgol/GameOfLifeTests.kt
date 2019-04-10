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
        println("Check original map")
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
        println("Original map passed")

        //Check that underpopulation rule has been applied
        println("Check new map")
        assertThat(newMap[0][0], `is`(true))
        assertThat(newMap[0][1], `is`(true))
        assertThat(newMap[1][0], `is`(true))
        assertThat(newMap[1][1], `is`(true))
        println("First cluster still living")

        assertThat(newMap[5][5], `is`(true))
        assertThat(newMap[5][6], `is`(true))
        assertThat(newMap[5][7], `is`(false))
        assertThat(newMap[6][5], `is`(true))
        println("Second cluster has expected death")

        assertThat(newMap[7][7], `is`(false))
        assertThat(newMap[8][8], `is`(false))
        println("Third cluster has died completely")
    }

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
        println("Check original map")
        assertThat(gameMap[0][0], `is`(true))
        assertThat(gameMap[0][1], `is`(true))
        assertThat(gameMap[1][0], `is`(true))
        assertThat(gameMap[1][1], `is`(true))

        assertThat(gameMap[5][5], `is`(true))
        assertThat(gameMap[5][6], `is`(true))
        assertThat(gameMap[5][7], `is`(true))
        assertThat(gameMap[6][5], `is`(true))
        println("Original map passed")

        println("Check that cells survive for 10 generations")

        for (i in 1..10) {
            var newMap = applyRules(newMap)
            assertThat(newMap[0][0], `is`(true))
            assertThat(newMap[0][1], `is`(true))
            assertThat(newMap[1][0], `is`(true))
            assertThat(newMap[1][1], `is`(true))

            assertThat(newMap[5][5], `is`(true))
            assertThat(newMap[5][6], `is`(true))
            assertThat(newMap[6][5], `is`(true))
        }
    }
}