package com.wearablehackphone;

import android.content.Context;
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
public class SpeechInputManager {
    private SpeechRecognizer sr = null;
    private Intent speechIntent = null;
    private Context context;
    private OnSpeechResultCallback onSpeechResultCallback;

    public SpeechInputManager(OnSpeechResultCallback onSpeechResultCallback) {
        this.onSpeechResultCallback = onSpeechResultCallback;
    }

    public void onCreate(Context context, Bundle savedInstanceState) {
        this.context = context;
        speechIntent = RecognizerIntent.getVoiceDetailsIntent(context);

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
                for(int i = 0; i < strlist.size(); i++) {
                    if (onSpeechResultCallback.onSpeechResultCallback(getBestResult(strlist))) {
                        break;
                    }
                }
                sr.startListening(speechIntent);
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                //Log.d("Speech", "onRmsChanged");
            }

        }
        if (sr == null) {
            sr = SpeechRecognizer.createSpeechRecognizer(context);
        }
        MyRecognitionListener listener = new MyRecognitionListener();
        sr.setRecognitionListener(listener);
    }

    private String getBestResult(ArrayList<String> strlist) {
        /* algorithm filter that tries to get ones with legit commands
            1. only want results that start with the prompt
            2. only want results that have a legit command
         */
        for (int i = 0; i < strlist.size(); i++) {
            String[] words = strlist.get(i).split(" ");
            if (words.length > 2) {
                if (VerbalCommandEngine.isPrompt(words[0]) && VerbalCommandEngine.isCommandWord(words[1])) {
                    //return removePromptAndCommand(strlist.get(i));
                    return strlist.get(i);
                }
            }
        }
        return strlist.get(0);
    }

    private String removePromptAndCommand(String phrase) {
        String[] words = phrase.split(" ");
        if (words.length <= 2) {
            return phrase;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < words.length; i++) {
            builder.append(words[i]).append(" ");
        }
        return builder.toString();
    }

    public void onResume() {
        sr.startListening(speechIntent);
    }

    public void onPause() {
        sr.cancel();
    }

    public void onDestroy() {
        sr.stopListening();
        sr.destroy();
    }
}
