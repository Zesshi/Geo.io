package net.ictcampus.geoio;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GuessTheFlagActivityTest {
GuessTheFlagActivity guessTheFlagActivity = new GuessTheFlagActivity();


@Test
    public void setUp() throws Exception {
        assertNotEquals(null, guessTheFlagActivity.button1.getText());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onSensorChanged() {
    }

    @Test
    public void onAccuracyChanged() {
    }

    @Test
    public void onResume() {
    }

    @Test
    public void onPause() {
    }

    @Test
    public void parseJson() {
    }
}