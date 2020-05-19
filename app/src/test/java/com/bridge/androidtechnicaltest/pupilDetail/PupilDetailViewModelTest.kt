package com.bridge.androidtechnicaltest.pupilDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.PupilPostBody
import com.bridge.androidtechnicaltest.ui.pupildetail.viewmodel.PupilDetailViewModel
import com.bridge.androidtechnicaltest.utility.RxImmediateSchedulerRule
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PupilDetailViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: IPupilRepository

    @Mock
    private lateinit var pupilDetailObserver: Observer<PupilDetailViewModel.PupilDetailState>

    private lateinit var pupilDetailViewModel: PupilDetailViewModel

    private val pupilPostObject = PupilPostBody("GB", "Name", "Img.jpeg", 0.0, 0.2)

    @Before
    fun setup() {
        pupilDetailViewModel = PupilDetailViewModel(repository)

        pupilDetailViewModel.pupilDetailObservable.observeForever(pupilDetailObserver)

    }

    @Test
    fun `Add student api call returns successfully`() {
        `when`(repository.addPupil(pupilPostObject)).thenReturn(Completable.complete())

        pupilDetailViewModel.addPupil(pupilPostObject)

        verify(pupilDetailObserver).onChanged(PupilDetailViewModel.PupilDetailState.Progress)
        verify(pupilDetailObserver).onChanged(PupilDetailViewModel.PupilDetailState.Success)
    }

    @Test
    fun `Add student api call returns with an error`() {
        `when`(repository.addPupil(pupilPostObject)).thenReturn(Completable.error(Throwable()))

        pupilDetailViewModel.addPupil(pupilPostObject)

        verify(pupilDetailObserver).onChanged(PupilDetailViewModel.PupilDetailState.Progress)
        verify(pupilDetailObserver).onChanged(PupilDetailViewModel.PupilDetailState.Error)
    }
}