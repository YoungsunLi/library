package net.lsun.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RootActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        //显示返回键
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ListView userList = findViewById(R.id.root_user_list);
        Button addUser = findViewById(R.id.root_add);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, arrayList);
        userList.setAdapter(arrayAdapter);

        SQLite sqLite = new SQLite(this, "userList", null, 1);
        sqLiteDatabase = sqLite.getReadableDatabase();
        final Cursor cursor = sqLiteDatabase.query("userList", null,null,null,null,null,null);

        String su;
        while (cursor.moveToNext()) {
            su = cursor.getString(3);
            switch (su) {
                case "root":
                    su = "系统管理员";
                    break;
                case "admin":
                    su = "图书管理员";
                    break;
            }
            arrayList.add("  用户："+cursor.getString(1)+"\n  密码："+cursor.getString(2)+"\n  权限："+su);
        }

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUser();
                cursor.close();
            }
        });

    }

    //返回键功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BootKey();
                Login();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void SetUser() {
        Intent intent = new Intent(this, SetUserActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void BootKey() {
        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("bootKey", "login");
        prefsEdit.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqLiteDatabase.close();

    }
}
