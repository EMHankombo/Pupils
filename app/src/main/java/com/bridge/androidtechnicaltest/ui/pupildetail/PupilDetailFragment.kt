package com.bridge.androidtechnicaltest.ui.pupildetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.db.PupilPostBody
import com.bridge.androidtechnicaltest.ui.pupildetail.viewmodel.PupilDetailViewModel
import com.bridge.androidtechnicaltest.utility.Utilities
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_pupildetail.*
import org.koin.android.viewmodel.ext.android.viewModel

class PupilDetailFragment : Fragment() {

    private val pupilDetailViewModel: PupilDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pupildetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_add.setOnClickListener {
            if (et_country.toString().trim().isNotEmpty() && et_name.toString().trim().isNotEmpty()) {
                pupilDetailViewModel.addPupil(pupilPostBody())

            }
        }

        pupilDetailViewModel.pupilDetailObservable.observe(viewLifecycleOwner, Observer {
            when (it) {
                is PupilDetailViewModel.PupilDetailState.Progress -> showProgress()
                is PupilDetailViewModel.PupilDetailState.Error -> handleError()
                is PupilDetailViewModel.PupilDetailState.Success -> handleSuccess()
            }
        })

        btn_retry.setOnClickListener {
            pupilDetailViewModel.addPupil(pupilPostBody())
        }
    }

    private fun pupilPostBody(): PupilPostBody {
        val country = et_country.text.toString()
        val name = et_name.text.toString()

        return PupilPostBody(country, name, IMAGE, Utilities.randomDouble(), Utilities.randomDouble())
    }

    private fun handleSuccess() {
        progressBar_pupilDetail.visibility = View.GONE
        group.visibility = View.VISIBLE
        error_container.visibility = View.GONE

        Toast.makeText(activity, getString(R.string.student_added_succesfully), Toast.LENGTH_LONG).show()
    }

    private fun handleError() {
        progressBar_pupilDetail.visibility = View.GONE
        error_container.visibility = View.VISIBLE
        group.visibility = View.GONE


    }

    private fun showProgress() {
        progressBar_pupilDetail.visibility = View.VISIBLE
        error_container.visibility = View.GONE
        group.visibility = View.GONE

    }

    companion object {
        fun newInstance(): PupilDetailFragment {
            return PupilDetailFragment()
        }

        const val IMAGE = "111111111111.jpg"
    }
}