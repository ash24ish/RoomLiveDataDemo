package com.example.roomlivedatademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";
    private EditText mEditWordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        final Button button = findViewById(R.id.button_save);
        mEditWordView = findViewById(R.id.edit_word);

        button.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())){
                setResult(RESULT_CANCELED, replyIntent);
            }
            else {
                String word = mEditWordView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY,word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
