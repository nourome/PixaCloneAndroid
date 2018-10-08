package noro.me.pixacloneandroid.ui.category

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.category_cell_layout.view.*
import kotlinx.android.synthetic.main.category_fragment.*
import noro.me.pixacloneandroid.R
import noro.me.pixacloneandroid.ui.adapters.CellAdapter


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

        if (caregoryGridView == null) {
            Log.d("Null", "Grid view is null")
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        caregoryGridView.adapter = CellAdapter(view!!.context, viewModel.cells)
        caregoryGridView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    viewModel.selectedCategory(v.cellNameText.text.toString())
                }

    }
}


