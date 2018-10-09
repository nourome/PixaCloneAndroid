package noro.me.pixacloneandroid.ui.start

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import kotlinx.android.synthetic.main.image_viewer_fragment.*
import kotlinx.android.synthetic.main.start_fragment.*
import noro.me.pixacloneandroid.R
import noro.me.pixacloneandroid.ui.adapters.OnPhotoSelectedInterface
import noro.me.pixacloneandroid.ui.category.CategoryFragment
import noro.me.pixacloneandroid.ui.collection.CollectionFragment
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment

class StartFragment : Fragment() {


    companion object {
        fun newInstance() = StartFragment()
    }

    private lateinit var viewModel: StartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.start_fragment, container, false)
        val searchView = view.findViewById<SearchView>(R.id.searchPixa)
        searchView.isActivated = true

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                searchView.clearFocus()
                keyword?.let {
                    val bundle = Bundle()
                    bundle.putSerializable("keyword",it)

                    fragmentManager?.beginTransaction()?.apply {
                        val fragment =  CollectionFragment.newInstance()
                        fragment.arguments = bundle
                        add(android.R.id.content, fragment)
                        addToBackStack(null)
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        commit()
                    }
                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                print("ignore")
                return false
            }

        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //use viewModel if you want to change background on every lunch
        viewModel = ViewModelProviders.of(this).get(StartViewModel::class.java)


    }



}
