package com.bridge.androidtechnicaltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.ui.pupillist.viewmodel.PupilListViewModel
import com.bridge.androidtechnicaltest.utility.RxImmediateSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class PupilsListViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: IPupilRepository

    @Mock
    private lateinit var pupilsStateObserver : Observer<PupilListViewModel.PupilListState>

    private lateinit var pupilListViewModel: PupilListViewModel

    private lateinit var pupilsResponse :PupilList

    private lateinit var pupil: Pupil

    @Before
    fun setup(){
        pupilListViewModel = PupilListViewModel(repository)

        pupilListViewModel.pupilsStateObservable.observeForever(pupilsStateObserver)

        pupil = Pupil(1L,"name1","GB","Image string",0.0,0.1)
        pupilsResponse = PupilList(mutableListOf(pupil))
    }

    @Test
    fun `get pupil api call returns successfully`(){
        `when`(repository.getOrFetchPupils()).thenReturn(Single.just(pupilsResponse))

        pupilListViewModel.getPupils()

        verify(pupilsStateObserver).onChanged(PupilListViewModel.PupilListState.Progress)
        verify(pupilsStateObserver).onChanged(PupilListViewModel.PupilListState.Success(pupilsResponse))
    }

    @Test
    fun `get pupils api call returns with an error`(){
        `when`(repository.getOrFetchPupils()).thenReturn(Single.error(RuntimeException()))

        pupilListViewModel.getPupils()

        verify(pupilsStateObserver).onChanged(PupilListViewModel.PupilListState.Progress)
        verify(pupilsStateObserver).onChanged(PupilListViewModel.PupilListState.Error)


    }
}