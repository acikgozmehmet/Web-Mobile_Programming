package com.vijaya.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private EditText inputQuestion;
    private Button QandABtn;

    private TextToSpeech mtts;
    private boolean ready = false;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String PREFS = "prefs";
//    private static final String NAME = "name";
    private static String NAME = "User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(PREFS,0);
        editor = preferences.edit();

        mtts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                {
                    mtts.setLanguage(Locale.US);
                    mtts.speak("Hello",TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        inputQuestion = (EditText) findViewById((R.id.textInput));
        QandABtn = (Button) findViewById((R.id.button2));

        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();

            }
        });

        QandABtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            QandA();
                                        }
                                    }

        );
    }

    private void QandA() {
        String text = inputQuestion.getText().toString();

        if (text != null )
        {
            String medAssText = "";

            if (text.toLowerCase().contains("hello")) {
                medAssText = "What is your name ?";
            }

            if (text.toLowerCase().contains("my name is"))
            {
                String [] speech = text.split(" ");
                NAME = speech[speech.length-1];
                medAssText = "Your name is " + NAME;
            }

            if (text.toLowerCase().contains("not feeling good"))
            {
                medAssText = "I can understand. Please tell your symptoms in short";
            }

            if (text.toLowerCase().contains("time")) {
                SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
                Date now = new Date();
                String[] strDate = simpledateformat.format(now).split(":");
                if (strDate[1].contains("00")) {
                    strDate[1] = "o'clock";
                }

                medAssText = "The time is "+ simpledateformat.format(now);
            }

            if (text.toLowerCase().contains("medicine"))
            {
                medAssText = "I think, you have fever. Please take this medicine";
            }

            if (text.toLowerCase().contains("thank you") && text.toLowerCase().contains("medical assistant"))
            {
                medAssText = "Thank you, too! " + NAME  + " take care";
            }

            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:red;\">" + NAME +"  >> " + text+ "</p>"));
            mtts.speak(medAssText, TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\"> Medical Assistant >> "+ medAssText));

        }

    } // QandA

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    mVoiceInputTv.setText(result.get(0));
                    recognize_text(result.get(0));
                }
                break;
            }

        }
    }


    private void recognize_text(String text){
        String name;
        String [] speech = text.split(" ");

        if (text.contains("hello")) {


            mtts.speak("What is your name", TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append("User >> " + text);
            mVoiceInputTv.append("Medical Assistant >> What is your name ?" );
        }

        if (text.contains("my name is"))
        {
            name = speech[speech.length-1];
            editor.putString(NAME, name).apply();
            mtts.speak("Your name is " +  preferences.getString(NAME, null), TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append("User >> name is "+ name);
            mVoiceInputTv.append("Medical Assistant >> Your name is " + preferences.getString(NAME, null) );
        }

        if (text.contains("not feeling good"))
        {
            mtts.speak("I can understand. Please tell your symptoms in short", TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append(preferences.getString(NAME, null) + " >> "+ text);
            mVoiceInputTv.append("Medical Assistant >>  can understand. Please tell your symptoms in short");
        }

        if (text.contains("time"))
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
            Date now = new Date();
            String [] strDate = simpledateformat.format(now).split(":");
            if (strDate[1].contains("00"))
            {
                strDate[1] = "o'clock";
            }

            mtts.speak("The time is "+ simpledateformat.format(now), TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append(preferences.getString(NAME, null) + " >> "+ text);
            mVoiceInputTv.append("Medical Assistant >>  The time is " + simpledateformat.format(now) );
        }


        if (text.contains("medicine"))
        {
            mtts.speak("I think, you have fever. Please have this medicine", TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append(preferences.getString(NAME, null) + " >> "+ text);
            mVoiceInputTv.append("Medical Assistant >>  I think, you have fever. Please have this medicine");
        }

        if (text.contains("thank you") && text.contains("medical assistant"))
        {
            mtts.speak("Thank you, too! " + preferences.getString(NAME, null) + " take care", TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.append(preferences.getString(NAME, null) + " >> "+ text);
            mVoiceInputTv.append("Medical Assistant >>  Thank you, too! " + preferences.getString(NAME, null) + " take care");
        }

    }
}