package com.example.usuario.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewNoteActivity extends AppCompatActivity {
    private final String PREFERENCES = "NotesPreferences";
    private final String CURRENT_INDEX = "index";
    private EditText title;
    private EditText text;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_note);
        title = (EditText) findViewById(R.id.edit_title);
        text = (EditText) findViewById(R.id.edit_text);
        buttonSave = (Button) findViewById(R.id.button_save);
        title.setHint(R.string.title_text);
        text.setHint(R.string.text_text);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().equals("") && !text.getText().toString().equals("")){
                    SharedPreferences sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
                    int newId = sp.getInt(CURRENT_INDEX, 0);
                    addRow(newId, title.getText().toString(), text.getText().toString());
                    SharedPreferences.Editor e = sp.edit();
                    e.putInt(CURRENT_INDEX, newId + 1);
                    e.apply();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.missing_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRow(int id, String title, String text){
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.dbName, Context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO " + MainActivity.tableName + " (id, title, content) VALUES (" + id + ", '" + title + "', '" + text + "')");
        db.close();
    }
}
