package kgol

import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.CoreMatchers.*

class GameOfLifeTests {
    @Test
    fun createGameMap() {
        var gameMap = GameMap(50, 50)
        assertThat(gameMap.xSize, `is`(50))
        assertThat(gameMap.ySize, `is`(50))
        assertThat(gameMap.cells[0][0], `is`(false))
        assertThat(gameMap.cells[24][13], `is`(false))
        assertThat(gameMap.cells[49][49], `is`(false))
    }
}