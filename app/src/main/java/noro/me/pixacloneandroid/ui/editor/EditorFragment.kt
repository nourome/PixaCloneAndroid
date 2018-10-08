package noro.me.pixacloneandroid.ui.editor

import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.*
import com.google.android.flexbox.R.attr.*
import com.google.android.flexbox.R.attr.layout_minWidth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import noro.me.pixacloneandroid.R
import noro.me.pixacloneandroid.model.EditorCollectionModel
import noro.me.pixacloneandroid.model.PixaPhotoModel
import noro.me.pixacloneandroid.model.ResponseStatus
import noro.me.pixacloneandroid.ui.adapters.CollectionRecyclerAdapter
import noro.me.pixacloneandroid.ui.adapters.OnPhotoSelectedInterface
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment


class EditorFragment: Fragment(), OnPhotoSelectedInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var collectionAdapter: CollectionRecyclerAdapter
    private var stopPrefetching = false
    private lateinit var flexboxLayoutManager: FlexboxLayoutManager

    companion object {
        fun newInstance() = EditorFragment()
    }

    private lateinit var viewModel: EditorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.collection_fragment, container, false)

        recyclerView = view.findViewById(R.id.collection_recycler_view)

        flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        val density = context!!.resources.displayMetrics.density
        collectionAdapter = CollectionRecyclerAdapter(arrayListOf(), density)
        collectionAdapter.fragment = this
        flexboxLayoutManager.findLastCompletelyVisibleItemPosition()

        recyclerView.apply {
            layoutManager = flexboxLayoutManager
            adapter = collectionAdapter
            setHasFixedSize(true)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView!!.layoutManager.itemCount
                if (!stopPrefetching && totalItemCount == flexboxLayoutManager.findLastCompletelyVisibleItemPosition() + 1) {
                    stopPrefetching = true
                    loadPhotosAsync()
                }
            }
        })

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)
        viewModel.model = EditorCollectionModel()
        viewModel.model?.data = null
        loadPhotosAsync()

    }

    override fun onItemSelected(position: Int) {
        //loadPhotosAsync()
        val bundle = Bundle()
        bundle.putSerializable("pixa",viewModel.photos[position])

        fragmentManager?.beginTransaction()?.apply {
            val imageViewerFragment =  ImageViewerFragment.newInstance()
            imageViewerFragment.arguments = bundle
            replace(android.R.id.content, imageViewerFragment)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }

    }

    private fun loadPhotosAsync() {

        viewModel.loadPhotos().observeOn(AndroidSchedulers.mainThread()).subscribe({

            when(it) {
                ResponseStatus.Failed -> {
                    stopPrefetching = true

                }
                else -> {
                    collectionAdapter.photos.addAll(viewModel.loadedItems)
                    stopPrefetching = false
                    collectionAdapter.notifyDataSetChanged()
                     }

        }
        },{
            stopPrefetching = true
            collectionAdapter.notifyDataSetChanged()
        }, {
        })
    }
}