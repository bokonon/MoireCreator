package moire.ys.moirecreator.ui.view.main

import android.content.Context
import android.support.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

import ys.moire.domain.usecase.SharedPreferencesManager
import ys.moire.presentation.presenter.main.MainPresenter
import ys.moire.presentation.ui.viewparts.MoireView

import android.support.test.InstrumentationRegistry.getInstrumentation
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * MainPresenterTest.
 */
@RunWith(AndroidJUnit4::class)
class MainPresenterTest {

    @Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    internal var moireViewMock: MoireView

    @Mock
    internal var preferencesManager: SharedPreferencesManager

    internal var context: Context

    @Before
    fun setup() {
        moireViewMock = mock(MoireView::class.java)
        preferencesManager = mock<SharedPreferencesManager>(SharedPreferencesManager::class.java)
        context = getInstrumentation().targetContext
    }

    @Test
    fun getMoireTypeTest() {
        val presenter = MainPresenter(context)
        presenter.captureCanvas()

        verify(moireViewMock, times(1)).width
        verify(moireViewMock, times(1)).height
        verify(moireViewMock, times(1)).captureCanvas(null)
    }

}
