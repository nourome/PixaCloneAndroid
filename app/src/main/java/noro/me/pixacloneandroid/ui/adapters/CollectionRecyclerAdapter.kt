package noro.me.pixacloneandroid.ui.adapters

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.cell_image_view.view.*
import noro.me.pixacloneandroid.R
import noro.me.pixacloneandroid.model.PixaPhotoModel
import com.google.android.flexbox.FlexboxLayoutManager
import noro.me.pixacloneandroid.ui.editor.EditorFragment
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment
import noro.me.pixacloneandroid.ui.start.StartFragment
import java.lang.Exception


class CollectionRecyclerAdapter(val photos: ArrayList<PixaPhotoModel>, val dp: Float): RecyclerView.Adapter<CollectionRecyclerAdapter.CellHolder>() {

    lateinit var fragment: Fragment


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionRecyclerAdapter.CellHolder {
        val cellView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_image_view, parent, false)
        return CellHolder(cellView)

    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: CollectionRecyclerAdapter.CellHolder, position: Int) {
        val pos = position % photos.size
        holder.bind(photos[pos], dp)
        holder.itemView.setOnClickListener {
            if (fragment is EditorFragment) {
                (fragment as EditorFragment).onItemSelected(position)
            }
        }


    }





    class CellHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private var pixaPhoto: PixaPhotoModel? = null

        private val imageView: ImageView = itemView.findViewById(R.id.cellImage)

        internal fun bindTo(@DrawableRes drawableRes: Int) {
            imageView.setImageResource(drawableRes)
            //Picasso.get().load(pixaPhoto.previewURL).into(view.cellImageView)
            val lp = imageView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f

            }
        }



        fun bind(pixaPhoto: PixaPhotoModel, dp: Float) {

            val lp = view.cellImage.layoutParams

            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f
                lp.width = (pixaPhoto.previewWidth * dp).toInt()
                lp.height = (pixaPhoto.previewHeight * dp).toInt()
            }

            print(lp.width)
             Picasso.get().load(pixaPhoto.previewURL).into(view.cellImage, object: Callback {

                 override fun onSuccess() {
                     print("good")
                 }

                 override fun onError(e: Exception?) {
                     print(e.toString())
                 }

             })
        }



    }
}



