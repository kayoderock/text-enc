package com.codegod.texenc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codegod.texenc.utils.AES;
import com.codegod.texenc.utils.Build;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TexEnc";

    Button go_button;
    EditText input_text;
    TextView output_textview;
    RadioGroup aes_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input_text = (EditText) findViewById(R.id.input_text_editText);
        output_textview = (TextView) findViewById(R.id.output_textview);
        aes_action = (RadioGroup) findViewById(R.id.aes_action);

        go_button = (Button) findViewById(R.id.go_button);
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked_radio_id = aes_action.getCheckedRadioButtonId();

                switch (checked_radio_id) {
                    case R.id.encrypt_radioButton:
                        if (checkInputText()) {
                            String encryptedString = AES.encrypt(input_text.getText().toString(), Build.KEY);

                            Log.d(TAG, encryptedString);

                            output_textview.setText(encryptedString);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please Enter a Text",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.decrypt_radioButton:
                        if (checkInputText()) {
                            String decryptedString = AES.decrypt(input_text.getText().toString(), Build.KEY);

                            output_textview.setText(decryptedString);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please Enter a Text",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (output_textview.getText().length() > 0) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, output_textview.getText().toString());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "No Text to Share",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public boolean checkInputText() {
        return input_text.getText().length() > 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
