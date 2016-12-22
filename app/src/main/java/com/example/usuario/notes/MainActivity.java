package com.example.usuario.notes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String dbName = "Notes.db";
    private final String tableName = "notes";
    private ListView lv;
    private Button buttonCreate;
    private ArrayList<Note> contents;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.notes_view);
        buttonCreate = (Button) findViewById(R.id.button_create_new);
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (id INT, title VARCHAR(255), content VARCHAR(8000))");
        contents = new ArrayList<>();
        Cursor s = db.rawQuery("SELECT * FROM " + tableName, null);
        int size = s.getCount();
        if(size > 0){
            for(int i = 0; i < size; i++){
                contents.add(new Note(s.getInt(0), s.getString(1), s.getString(2)));
                s.moveToNext();
            }
        }
        s.close();
        ArrayAdapter adapter= new NoteAdapter(this, contents);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), NewNoteActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    private class NoteAdapter extends ArrayAdapter<Note>{
        ArrayList<Note> notes;
        Context context;

        public NoteAdapter(Context context, ArrayList<Note> notes){
            super(context, R.layout.activity_list_item, notes);
            this.context = context;
            this.notes = notes;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Note n = notes.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null)
                convertView = inflater.inflate(R.layout.activity_list_item, parent, false);


            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            title.setText(n.getTitle());
            return convertView;
        }
    }
}
