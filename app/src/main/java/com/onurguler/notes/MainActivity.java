package com.onurguler.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView lst;
    private LinearLayout pnlButtons;
    private ArrayList<String> fileNames;
    private ArrayAdapter arrayAdapter;

    private boolean longClicked = false;
    private View selectedItem;
    private String selectedItemName;
    private final int COLOR = 0xff33b5e5;
    private boolean clicked = false;

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if(clicked)
            {
                clicked = false;
                longClicked = false;
                cancel(selectedItem);
            }
            else if(!longClicked)
            {
                String title = fileNames.get(position);

                Intent intent = new Intent(MainActivity.this, ReaderActivity.class);
                intent.putExtra("title", title);
                MainActivity.this.finish();
                startActivity(intent);
            }
            else
            {
                clicked = true;
            }
        }
    };

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener()
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            longClicked = true;
            selectedItem = view;
            selectedItemName = fileNames.get(position);

            for(int a = 0; a < parent.getChildCount(); a++)
            {
                parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
            }

            view.setBackgroundColor(COLOR);

            pnlButtons.setVisibility(View.VISIBLE);

            return false;
        }
    };


    private void init()
    {
        lst = findViewById(R.id.lst);
        pnlButtons = findViewById(R.id.pnlButtons);

        fileNames = Controller.getFiles(this);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames);

        lst.setAdapter(arrayAdapter);

        lst.setOnItemClickListener(onItemClickListener);
        lst.setOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void createNew(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("File Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String title = input.getText().toString();
                Controller.edit = false;

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("title", title);
                MainActivity.this.finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void delete(View v)
    {
        longClicked = false;
        Controller.deleteFiles(this,selectedItemName);
        arrayAdapter.remove(selectedItemName);
        pnlButtons.setVisibility(View.INVISIBLE);
    }

    public void cancel(View v)
    {
        longClicked = false;
        pnlButtons.setVisibility(View.INVISIBLE);
        selectedItem.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
