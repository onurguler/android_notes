package com.onurguler.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReaderActivity extends AppCompatActivity {

    private TextView txt;
    private String title;
    private String text;
    private void init()
    {
        txt = findViewById(R.id.txt);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        text = Controller.readNote(this, title+".txt");
        txt.setText(text);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        init();
    }

    public void edit(View v)
    {
        Controller.edit = true;

        Intent intent = new Intent(ReaderActivity.this, EditorActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        ReaderActivity.this.finish();
        startActivity(intent);
    }

    public void exit(View v)
    {
        Intent intent = new Intent(ReaderActivity.this, MainActivity.class);
        ReaderActivity.this.finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(ReaderActivity.this, MainActivity.class);
        ReaderActivity.this.finish();
        startActivity(intent);
    }
}
