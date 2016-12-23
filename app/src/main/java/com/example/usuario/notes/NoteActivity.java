package com.example.usuario.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {
    private Note note;
    private EditText title;
    private EditText text;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_note);
        Bundle b = this.getIntent().getExtras();
        if(b != null)
            note = (Note) b.getSerializable("note");

        title = (EditText) findViewById(R.id.edit_title);
        title.setText(note.getTitle());
        text = (EditText) findViewById(R.id.edit_text);
        text.setText(note.getText());
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setTitle(title.getText().toString());
                note.setText(text.getText().toString());
                updateRow();
                finish();
            }
        });
    }

    private void updateRow(){
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE " + MainActivity.tableName + " SET title='" + note.getTitle() + "', content='" + note.getText() + "' WHERE id=" + note.getId() + ";");
        db.close();
    }
}
