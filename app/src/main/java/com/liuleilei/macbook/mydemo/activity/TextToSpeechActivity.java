package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.liuleilei.macbook.mydemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * create by liu
 * on2019/6/20
 */
public class TextToSpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {
    private List<TextToSpeech.EngineInfo> list = new ArrayList<>();
    private TextView textContent;
    private TextView play;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        textContent = findViewById(R.id.text_content);
        play = findViewById(R.id.play);
        play.setOnClickListener(this);

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, TextToSpeechActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    private void initTextSpeech() {
        textToSpeech = new TextToSpeech(getApplicationContext(), this);
        list = textToSpeech.getEngines();
        Set<Locale> localeSet = textToSpeech.getAvailableLanguages();
        Set<Voice> voiceSet = textToSpeech.getVoices();
        String defaultEngine = textToSpeech.getDefaultEngine();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.SIMPLIFIED_CHINESE);
            textToSpeech.setPitch(1.0f);
            textToSpeech.setSpeechRate(1.0f);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //textToSpeech.speak(textContent.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                startActivity(new Intent("com.android.settings.TTS_SETTINGS"));
            } else {
                textToSpeech.speak(textContent.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                initTextSpeech();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();

    }

    public void shutUp() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    public void destroy() {
        if (textToSpeech != null) {
            shutUp();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
}
