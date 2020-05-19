package com.bridge.androidtechnicaltest.ui.pupildetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.PupilPostBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PupilDetailViewModel(private val repository: IPupilRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _pupilDetailObservable = MutableLiveData<PupilDetailState>()
    val pupilDetailObservable: LiveData<PupilDetailState>
        get() = _pupilDetailObservable

    fun addPupil(pupil: PupilPostBody) {
        _pupilDetailObservable.value = PupilDetailState.Progress
        compositeDisposable.add(
                repository.addPupil(pupil)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            _pupilDetailObservable.value = PupilDetailState.Success

                        }, {
                            _pupilDetailObservable.value = PupilDetailState.Error

                        })
        )
    }

    sealed class PupilDetailState {
        object Progress : PupilDetailState()
        object Error : PupilDetailState()
        object Success : PupilDetailState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}