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
        for(i in 1..60)
        {
            val randomValue = List(1) { Random.nextInt(0, 1) }
            println(randomValue)
            if(randomValue[0] == 1)
                cellsList.add(Cell(true))
            else
                cellsList.add(Cell(true))
        }

        adapter = LifeAdapter(this, cellsList)

        gridview.adapter = adapter
    }
}
