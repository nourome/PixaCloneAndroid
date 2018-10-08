package noro.me.pixacloneandroid.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.category_cell_layout.view.*
import noro.me.pixacloneandroid.R

class CellAdapter(private val mContext: Context, private val cells: List<Pair<String, Int>>): BaseAdapter() {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        lateinit var  cellView: View


        if (convertView == null) {
            val layoutInflater  = LayoutInflater.from(mContext);
            cellView = layoutInflater.inflate(R.layout.category_cell_layout, null);

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            options.inSampleSize = 3


            val icon = BitmapFactory.decodeResource(mContext.resources,
                    cells[position].second, options)

            cellView.cellImageView.setImageBitmap(icon)
            cellView.cellNameText.text = cells[position].first
        }else {
            cellView = convertView
        }

        return cellView
    }

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    override fun getCount(): Int  = cells.size

}