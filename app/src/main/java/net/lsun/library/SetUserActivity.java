package net.lsun.library;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SetUserActivity extends Activity {
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        final EditText setRootUser = findViewById(R.id.set_root_user);
        final EditText setRootPwd = findViewById(R.id.set_root_pwd);
        Button setRootSave = findViewById(R.id.set_root_save);
        final RadioButton setSu = findViewById(R.id.add_user_rb_root);

        SQLite sqLite = new SQLite(this, "userList", null, 1);
        sqLiteDatabase = sqLite.getReadableDatabase();

        SharedPreferences prefs = getSharedPreferences("Prefs",MODE_PRIVATE);
        final SharedPreferences.Editor prefsEdit = prefs.edit();

        setRootSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = setRootUser.getText().toString();
                String pwd = setRootPwd.getText().toString();
                Cursor cursor = sqLiteDatabase.query("userList", null,null,null,null,null,null);

                //判断用户是否输入了注册信息
                boolean is = true;
                if (user.equals("") && pwd.equals("")) {
                    Toast("请输入用户名和密码！");
                } else {
                    if (user.equals("")) {
                        Toast("你还没有填写用户名！");
                    } else if (pwd.equals("")) {
                        Toast("你还没有填写密码！");
                    } else {

                        while (cursor.moveToNext()) {
                            if (user.equals(cursor.getString(1))) {
                                Toast("用户已存在！");
                                is = false;
                                break;
                            }
                        }
                        cursor.close();
                    }
                    if (is) {
                        String su = "admin";
                        if (setSu.isChecked()) {
                            su = "root";
                        }

                        dbAdd(user,pwd, su);
                        prefsEdit.putString("bootKey", "root");
                        prefsEdit.apply();
                        Toast("注册成功!");
                        Root();
                    }
                }
            }
        });
    }
    private void Toast(String tips) {
        Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();
    }

    //跳到登陆页
    private void Root() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        SetUserActivity.this.finish();
    }

    //SQLite
    private void dbAdd(String user, String pwd, String su) {
        sqLiteDatabase.execSQL("insert into userList (user, pwd, su) values ('" + user + "','" + pwd + "','" + su + "')");
        sqLiteDatabase.close();
    }

    @Override
    public void onBackPressed() {
        Root();
    }
}
