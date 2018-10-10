package noro.me.pixacloneandroid.ui.imageViewer

import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import noro.me.pixacloneandroid.R
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import noro.me.pixacloneandroid.model.PixaPhotoModel
import java.lang.Exception


class ImageViewerFragment: Fragment() {

    private lateinit var viewModel: ImageViewerViewModel
    private var pixaPhoto: PixaPhotoModel? = null

    companion object {
        fun newInstance() = ImageViewerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pixaPhoto = arguments?.getSerializable("pixa") as? PixaPhotoModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.image_viewer_fragment, container, false)
        val photoView = view.findViewById(R.id.photo_view) as PhotoView
        if(pixaPhoto != null) {
            Picasso.get().load(pixaPhoto!!.webformatURL).into(photoView, object : Callback {
                override fun onSuccess() {
                    print("good")
                }

                override fun onError(e: Exception?) {
                   Log.e("Pixa", e.toString())
                    print("bad")
                }
            })
        } else {
            photoView.setImageResource(R.drawable.pixa_not_found)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ImageViewerViewModel::class.java)

    }
}