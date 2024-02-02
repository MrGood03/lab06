package com.example.lab6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    int sel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adp= new ArrayAdapter<Note>( this, android.R.layout.simple_list_item_1);
        ListView lst = findViewById(R.id.lst_notes);
        lst.setAdapter(adp);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sel=position;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode==12345) {
            if (data != null) {
                int pas = data.getIntExtra("my-note-index", 1);
                String title = data.getStringExtra("my-note-title");
                String content = data.getStringExtra("my-note-content");
                Note n = adp.getItem(pas);
                n.title = title;
                n.content = content;
                adp.notifyDataSetChanged();

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void OnNewClick (View v)
    {
        Note n= new Note();
        n.title="New note";
        n.content="Some content";
        adp.add(n);
        int pas= adp.getPosition(n);
        Intent i = new Intent(this,MainActivity2.class);
        i.putExtra("my-note-index",pas);
        i.putExtra("my-note-title", n.title);
        i.putExtra("my-note-content",n.content);

        startActivityForResult(i,12345);


    }
    public void OnEditClick (View v)
    {
        Note n = adp.getItem(sel);
        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("my-note-index", sel);
        i.putExtra("my-note-title", n.title);
        i.putExtra("my-note-content", n.content);
        startActivityForResult(i, 12345);

    }
    public void OnDeleteClick (View v)
    {

        Note n = adp.getItem(sel);
        Intent i = new Intent(this,MainActivity2.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы ходитет удалить данную запись?");
        builder.setMessage("Вы уверены?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
            @Override
            public void  onClick(DialogInterface dialog, int which)
            {
                adp.remove(n);
            }});


        builder.setNegativeButton("NO", null );
        builder.show();



    }
}