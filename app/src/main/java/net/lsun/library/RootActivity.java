package net.lsun.library;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        final ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<String> arrayListID = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, arrayList);
        userList.setAdapter(arrayAdapter);

        final SQLite sqLite = new SQLite(this, "userList", null, 1);
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
            arrayListID.add(cursor.getString(cursor.getColumnIndex("user")));
        }

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUser();
                cursor.close();
            }
        });

        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = {"确定","取消"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RootActivity.this);
                builder.setTitle("删除 " + arrayListID.get(position) + "？").setItems(items, new DialogInterface.OnClickListener() {

                    //弹窗点击
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                //单引号转字符串
                                String sql = "delete from userList where user='" + arrayListID.get(position)+"'";
                                sqLiteDatabase.execSQL(sql);
                                arrayListID.remove(position);
                                arrayList.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                break;
                        }
                    }
                }).create().show();
                return false;
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
