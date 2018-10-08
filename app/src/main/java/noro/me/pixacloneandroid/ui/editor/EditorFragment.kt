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
import noro.me.pixacloneandroid.ui.adapters.RecyclerViewHelper
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment


class EditorFragment: Fragment(), OnPhotoSelectedInterface {

    //private lateinit var recyclerView: RecyclerView
    //private lateinit var collectionAdapter: CollectionRecyclerAdapter
    //private var stopPrefetching = false
    //private lateinit var flexboxLayoutManager: FlexboxLayoutManager
    private lateinit var recyclerViewHelper: RecyclerViewHelper

    companion object {
        fun newInstance() = EditorFragment()
    }

    //private lateinit var viewModel: EditorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.collection_fragment, container, false)

        recyclerViewHelper = RecyclerViewHelper(context!!, this, view.findViewById(R.id.collection_recycler_view))
        //recyclerView = view.findViewById(R.id.collection_recycler_view)

        /*flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        val density = context!!.resources.displayMetrics.density
        collectionAdapter = CollectionRecyclerAdapter(arrayListOf(), density)
        collectionAdapter.fragment = this

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
*/
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewHelper.viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)
        recyclerViewHelper.viewModel.model = EditorCollectionModel()
        recyclerViewHelper.viewModel.model?.data = null

        recyclerViewHelper.loadPhotosAsync()

    }

    override fun onItemSelected(position: Int) {

        val bundle = Bundle()
        bundle.putSerializable("pixa",recyclerViewHelper.viewModel.photos[position])

        fragmentManager?.beginTransaction()?.apply {
            val imageViewerFragment =  ImageViewerFragment.newInstance()
            imageViewerFragment.arguments = bundle
            add(android.R.id.content, imageViewerFragment)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }

    }
/*
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
    */
}