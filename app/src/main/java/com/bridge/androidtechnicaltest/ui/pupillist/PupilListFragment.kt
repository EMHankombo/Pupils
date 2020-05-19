package com.bridge.androidtechnicaltest.ui.pupillist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.ui.pupildetail.PupilDetailFragment
import com.bridge.androidtechnicaltest.ui.pupillist.adapter.PupilsAdapter
import com.bridge.androidtechnicaltest.ui.pupillist.viewmodel.PupilListViewModel
import com.bridge.androidtechnicaltest.utility.Utilities
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_pupillist.*
import org.koin.android.viewmodel.ext.android.viewModel

class PupilListFragment : Fragment() {

    private val pupilListViewModel: PupilListViewModel by viewModel()
    private lateinit var pupilsAdapter: PupilsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pupillist, container, false)

        pupilListViewModel.getPupils(Utilities.isConnected(activity))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        pupilListViewModel.pupilsStateObservable.observe(viewLifecycleOwner, Observer {
            when (it) {
                is PupilListViewModel.PupilListState.Success -> handleSuccess(it.pupils)
                is PupilListViewModel.PupilListState.Error -> handleError()
                is PupilListViewModel.PupilListState.Progress -> showProgress()
            }
        })

        btn_retry.setOnClickListener {
            pupilListViewModel.getPupils(Utilities.isConnected(activity))
        }

        btn_add_pupil.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PupilDetailFragment.newInstance())
                    ?.addToBackStack(null)?.commit()
        }

    }

    private fun initRecyclerView() {
        pupilsAdapter = PupilsAdapter(mutableListOf())
        val recyclerView = pupil_list
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = pupilsAdapter
        }
    }

    private fun handleSuccess(pupils: PupilList) {
        progressBar.visibility = View.GONE
        btn_add_pupil.visibility = View.VISIBLE
        error_container.visibility = View.GONE
        pupil_list.visibility = View.VISIBLE
        pupilsAdapter.setPupilsData(pupils.items)
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        btn_add_pupil.visibility = View.GONE
        error_container.visibility = View.GONE
        pupil_list.visibility = View.GONE

    }

    private fun handleError() {
        progressBar.visibility = View.GONE
        error_container.visibility = View.VISIBLE
        pupil_list.visibility = View.GONE

    }


}