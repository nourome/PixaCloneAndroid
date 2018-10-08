package noro.me.pixacloneandroid.ui.adapters

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import noro.me.pixacloneandroid.model.ResponseStatus
import noro.me.pixacloneandroid.ui.editor.EditorViewModel

class RecyclerViewHelper(private val context: Context, private val fragment: Fragment, private var recyclerView: RecyclerView ) {

    private  var collectionAdapter: CollectionRecyclerAdapter
    private var stopPrefetching = false
    private  var flexboxLayoutManager: FlexboxLayoutManager
    lateinit var viewModel: EditorViewModel

    init {

        flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        val density = context!!.resources.displayMetrics.density
        collectionAdapter = CollectionRecyclerAdapter(arrayListOf(), density)
        collectionAdapter.fragment = fragment

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
    }

    fun loadPhotosAsync() {

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
