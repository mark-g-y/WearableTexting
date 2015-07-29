package com.wearablehackphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Mark on 13/12/2014.
 */
public class Input extends Activity {

    private SpeechRecognizer sr = null;
    private Intent speechIntent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechIntent = RecognizerIntent.getVoiceDetailsIntent(getApplicationContext());

        class MyRecognitionListener implements RecognitionListener {

            @Override
            public void onBeginningOfSpeech() {
                Log.d("Speech", "onBeginningOfSpeech");
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.d("Speech", "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.d("Speech", "onEndOfSpeech");
            }

            @Override
            public void onError(int error) {
                Log.d("Speech", "onError" + error);
                sr.startListening(speechIntent);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                Log.d("Speech", "onEvent");
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.d("Speech", "onPartialResults");
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("Speech", "onReadyForSpeech");
            }


            @Override
            public void onResults(Bundle results) {
                Log.d("Speech", "onResults");
                ArrayList<String> strlist = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                for (int i = 0; i < strlist.size(); i++) {
                    Log.d("Speech", "result=" + strlist.get(i));
                }
                sr.startListening(speechIntent);
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                //Log.d("Speech", "onRmsChanged");
            }

        }
        if (sr == null) {
            sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        }
        MyRecognitionListener listener = new MyRecognitionListener();
        sr.setRecognitionListener(listener);
    }

    public void onResume() {
        super.onResume();
        sr.startListening(speechIntent);
    }

    public void onPause() {
        super.onPause();
        sr.cancel();
    }

    public void onDestroy() {
        super.onDestroy();
        sr.stopListening();
        sr.destroy();
    }
}
