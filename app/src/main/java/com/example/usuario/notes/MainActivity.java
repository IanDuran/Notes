package com.example.usuario.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final String dbName = "Notes.db";
    private final String tableName = "notes";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (title VARCHAR(255), content VARCHAR(MAX))");
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
