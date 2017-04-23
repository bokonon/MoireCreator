package moire.ys.moirecreator.ui.view.main;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import ys.moire.domain.SharedPreferencesManager;
import ys.moire.ui.view.main.MainPresenter;
import ys.moire.ui.view.moire.MoireView;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * MainPresenterTest.
 */
@RunWith(AndroidJUnit4.class)
public class MainPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MoireView moireViewMock;

    @Mock
    SharedPreferencesManager preferencesManager;

    Context context;

    @Before
    public void setup() {
        moireViewMock = mock(MoireView.class);
        preferencesManager = mock(SharedPreferencesManager.class);
        context = getInstrumentation().getTargetContext();
    }

    @Test
    public void getMoireTypeTest() {
        MainPresenter presenter = new MainPresenter(context);
        presenter.captureCanvas();

        verify(moireViewMock, times(1)).getWidth();
        verify(moireViewMock, times(1)).getHeight();
        verify(moireViewMock, times(1)).captureCanvas(null);
    }

}
