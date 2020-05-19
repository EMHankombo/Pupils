package com.bridge.androidtechnicaltest.ui.pupillist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.PupilList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PupilListViewModel(private val repository: IPupilRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _pupilStateObservable = MutableLiveData<PupilListState>()

    val pupilsStateObservable: LiveData<PupilListState>
        get() = _pupilStateObservable

    fun getPupils() {
        _pupilStateObservable.value = PupilListState.Progress
        compositeDisposable.add(
                repository.getOrFetchPupils()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ pupils ->
                            _pupilStateObservable.value = PupilListState.Success(pupils)

                        }, {
                            _pupilStateObservable.value = PupilListState.Error
                        })
        )
    }

    sealed class PupilListState {
        object Progress : PupilListState()
        object Error : PupilListState()
        data class Success(val pupils: PupilList) : PupilListState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}