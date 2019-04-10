package com.example.gameoflife_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var adapter: LifeAdapter? = null
    private var cellsList = ArrayList<Cell>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //load
        /*for(i in 1..60)
        {
            val randomValue = List(1) { Random.nextInt(0, 1) }
            println(randomValue)
            if(randomValue[0] == 1)
                cellsList.add(Cell(true))
            else
                cellsList.add(Cell(true))
        }*/

        val xSize: Int = 30
        val ySize: Int = 30


        for(y in 0 until ySize) {
            for (x in 0 until xSize) {
                val randomValue = List(1) { Random.nextInt(0, 2) }
                println(randomValue)
                if(randomValue[0] == 1)
                    cellsList.add(Cell(true))
                else
                    cellsList.add(Cell(true))
            }
        }
        adapter = LifeAdapter(this, cellsList)
        grid.columnCount = xSize
        grid.rowCount = ySize


        //gridTable.adapter = adapter
    }
}
