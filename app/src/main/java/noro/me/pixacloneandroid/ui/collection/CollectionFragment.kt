package noro.me.pixacloneandroid.ui.collection

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import noro.me.pixacloneandroid.R
import noro.me.pixacloneandroid.model.CategoryCollectionModel
import noro.me.pixacloneandroid.model.SearchCollectionModel
import noro.me.pixacloneandroid.ui.adapters.OnPhotoSelectedInterface
import noro.me.pixacloneandroid.ui.adapters.RecyclerViewHelper
import noro.me.pixacloneandroid.ui.editor.EditorViewModel
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment

class CollectionFragment: Fragment(), OnPhotoSelectedInterface {

    private lateinit var recyclerViewHelper: RecyclerViewHelper
    private var category: String? = null
    private var keyword: String? = null

    companion object {
        fun newInstance() = CollectionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.collection_fragment, container, false)
        category = arguments?.getString("category")
        keyword = arguments?.getString("keyword")

        recyclerViewHelper = RecyclerViewHelper(context!!, this, view.findViewById(R.id.collection_recycler_view))

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewHelper.viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)
        if (category != null) {
            recyclerViewHelper.viewModel.model = CategoryCollectionModel()
            recyclerViewHelper.viewModel.model?.data = category
        }else {
            recyclerViewHelper.viewModel.model = SearchCollectionModel()
            recyclerViewHelper.viewModel.model?.data = keyword
        }
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

}