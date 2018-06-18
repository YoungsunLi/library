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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private SQLite sqLite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        final EditText etUser = findViewById(R.id.login_edit_userName);
        final EditText etPwd = findViewById(R.id.login_edit_pwd);
        final CheckBox cbPwd = findViewById(R.id.login_chk_rm_pwd);
        Button login = findViewById(R.id.login_btn_login);

        sqLite = new SQLite(this, "userList", null, 1);

        login.setOnClickListener(new View.OnClickListener() {
            private Cursor cursor;

            @Override
            public void onClick(View v) {
                //读取edit的内容
                String user = String.valueOf(etUser.getText());
                String pwd = String.valueOf(etPwd.getText());

                //判断用户是否输入了登陆信息
                if (user.equals("") && pwd.equals("")) {
                    Toast("请输入用户名和密码!");
                } else if (user.equals("")) {
                    Toast("你还没有输入用户名！");
                } else if (pwd.equals("")) {
                    Toast("你还没有输入密码！");
                } else {
                    //读取数据库的内容
                    //new一个DBHelper对象并传递参数过去
                    SQLite sqLite = new SQLite(LoginActivity.this, "userList", null, 1);
                    //取得SQLiteDatabase的一个有写入权限的对象sqLiteDatabase
                    SQLiteDatabase sqLiteDatabase  = sqLite.getWritableDatabase();
                    //通过table获取当前查询的位置
                    cursor = sqLiteDatabase.query("userList", null, null, null, null, null, null);

                    //循环判断用户名和密码是否存在
                    boolean userVerify = false;
                    String su = "";
                    while(cursor.moveToNext()) {
                        if (user.equals(cursor.getString(1)) && pwd.equals(cursor.getString(2))) {
                            userVerify = true;
                            su = cursor.getString(3);
                            break;
                        }
                    }
                    //关闭数据库
                    sqLiteDatabase.close();
                    //如果存在
                    if (userVerify) {

                        //判断自动登陆chk是否选中，写入
                        if (cbPwd.isChecked()) {

                            SharedPreferences prefs = getSharedPreferences("Prefs",MODE_PRIVATE);
                            final SharedPreferences.Editor prefsEdit = prefs.edit();
                            //记录自动登陆
                            prefsEdit.putString("bootKey", su);
                            prefsEdit.apply();
                        }

                        if (su.equals("root")) {
                            Root();
                        } else if (su.equals("admin")){
                            Admin();
                        }

                        Toast("登陆成功！");

                    } else {
                        Toast("用户名或密码错误！");
                    }
                }
            }
        });
    }

    private void Toast(String tips) {
        Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();
    }

    private void Root() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void Admin() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}
