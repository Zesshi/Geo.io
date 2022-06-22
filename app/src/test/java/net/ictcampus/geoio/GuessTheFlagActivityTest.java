package net.ictcampus.geoio;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.os.Looper;

import org.junit.jupiter.api.Test;

class GuessTheFlagActivityTest {

    GuessTheFlagActivity guessTheFlagActivity = new GuessTheFlagActivity();
    @Test
    void onCreate() {
        //arrange
        Bundle savedInstanceState = new Bundle();
        //exception
        when(Looper.getMainLooper()).thenReturn(null);
        doNothing().when(Looper.getMainLooper());
        //act
        this.guessTheFlagActivity.onCreate(savedInstanceState);
        //assert
        assertNotEquals(null, this.guessTheFlagActivity.button1.getText());

    }
}