package com.simplewave.wave.simplewaveview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.simplewave.wave.library.WaveView;

public class MainAcvitity extends Activity {
    WaveView mWave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mWave = (WaveView) findViewById(R.id.id_waveview);
        mWave.setBaseLine(500);
    }
}
