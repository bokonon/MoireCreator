package moire.ys.moirecreator;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ys.moire.util.PreferencesUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * PreferenceUtil test class.
 */
@RunWith(AndroidJUnit4.class)
public class PreferencesUtilTest {

    private static final String TEST_KEY = "test_pref_key";

    private static final int TEST_ARG = 123;

    @Test
    public void putIntTest() throws Exception {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext();
        PreferencesUtil.putInt(context, TEST_KEY, TEST_ARG);

        final int result = PreferencesUtil.getInt(context, TEST_KEY);
        assertThat(result, is(TEST_ARG));
    }
}
