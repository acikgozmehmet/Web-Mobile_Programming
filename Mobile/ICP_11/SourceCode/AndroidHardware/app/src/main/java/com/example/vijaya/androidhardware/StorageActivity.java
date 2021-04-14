package com.example.vijaya.androidhardware;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {
    EditText txt_content;
    EditText contenttoDisplay;
    String FILENAME = "MyAppStorage1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txt_content = (EditText) findViewById(R.id.id_txt_mycontent);
        contenttoDisplay = (EditText) findViewById(R.id.id_txt_display);
    }

    public void saveTofile(View v) throws IOException {

        // ICP Task4: Write the code to save the text
        String text = txt_content.getText().toString();
        text += '\n';
        FileOutputStream fileout = null;
        try {
            fileout = openFileOutput(FILENAME, MODE_APPEND);
            fileout.write(text.getBytes());
            txt_content.getText().clear();
            Toast.makeText(this, "save to" + getFilesDir()+ "/" + FILENAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileout != null) {
                fileout.close();
            }
        }
    }

    public void retrieveFromFile(View v) throws IOException {

        // ICP Task4: Write the code to display the above saved text
        FileInputStream filein = null;

        try {

            filein = openFileInput(FILENAME);
            InputStreamReader instream = new InputStreamReader(filein);
            BufferedReader bufferedReader = new BufferedReader(instream);
            StringBuilder stringBuilder = new StringBuilder();
            String txt ;
            while ((( txt = bufferedReader.readLine()) != null)){
                stringBuilder.append(txt).append("\n");
            }

            contenttoDisplay.setText(stringBuilder.toString());
            contenttoDisplay.setVisibility(View.VISIBLE);

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (filein != null){
                try {
                    filein.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }
}
