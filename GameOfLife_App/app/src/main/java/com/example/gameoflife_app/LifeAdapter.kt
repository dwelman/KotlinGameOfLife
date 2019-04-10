//acts as the middleman between the view and the data source

package com.example.gameoflife_app

import android.content.Context;
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import kotlinx.android.synthetic.main.cell_layout.view.*

class LifeAdapter(var context:Context, var cellsList: ArrayList<Cell>) : BaseAdapter()
{
    //return the number of cells to render
    override fun getCount(): Int {
        return cellsList.size
    }

    //returns position
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return cellsList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cell = this.cellsList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var cellView = inflator.inflate(R.layout.cell_layout, null)
        cellView.imgCell.setImageResource(R.drawable.black_square)

        return cellView
    }
}