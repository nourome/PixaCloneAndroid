package noro.me.pixacloneandroid.ui.category

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.category_cell_layout.view.*
import kotlinx.android.synthetic.main.category_fragment.*
import noro.me.pixacloneandroid.R
import noro.me.pixacloneandroid.ui.adapters.CellAdapter
import noro.me.pixacloneandroid.ui.collection.CollectionFragment
import noro.me.pixacloneandroid.ui.imageViewer.ImageViewerFragment


class CategoryFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.category_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        caregoryGridView.adapter = CellAdapter(view!!.context, viewModel.cells)
        caregoryGridView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    val category = viewModel.selectedCategory(v.cellNameText.text.toString())
                    val bundle = Bundle()
                    bundle.putString("category", category)

                    fragmentManager?.beginTransaction()?.apply {
                        val fragment =  CollectionFragment.newInstance()
                        fragment.arguments = bundle
                        replace(android.R.id.content, fragment)
                        addToBackStack(null)
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        commit()
                    }
                }

    }
}


