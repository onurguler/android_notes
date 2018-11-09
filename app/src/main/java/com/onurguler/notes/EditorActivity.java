package com.onurguler.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity
{

    private String title;
    private EditText txtInput;

    private void init()
    {
        txtInput = findViewById(R.id.txtInput);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        if(Controller.edit) {
            String text = intent.getStringExtra("text");
            txtInput.setText(text);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        init();
    }

    public void save(View v)
    {
        String text = txtInput.getText().toString();
        Controller.generateNote(this, title + ".txt", text);

        onBackPressed();
    }

    public void cancel(View v)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(EditorActivity.this, MainActivity.class);
        EditorActivity.this.finish();
        startActivity(intent);
    }

}
