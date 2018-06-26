package com.dht.notes.testcode.ontouch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dht.notes.R;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class TestToastActivity extends Activity {


    private EditText myedit1;
    private EditText myedit2;
    private Button mybtn1;
    private Button mybtn2;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toast);
        bindView();
    }

    private void bindView() {

        myedit1 = (EditText) findViewById(R.id.myedit1);
        myedit2 = (EditText) findViewById(R.id.myedit2);
        mybtn1 = (Button) findViewById(R.id.mybtn1);
        mybtn2 = (Button) findViewById(R.id.mybtn2);
        imageView = (ImageView) findViewById(R.id.image1);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        System.out.println("bitmap = " + bitmap);
        imageView.setImageBitmap(bitmap);


        //提交按钮
        mybtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = myedit1.getText().toString();
                String password = myedit2.getText().toString();
                if (false || true) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //测试数据 用户名为：wang ,密码为：123
                //对用户名或密码进行判断
                if ("wang".equals(username) && "123".equals(password)) {
                    Toast.makeText(getApplicationContext(), "正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //重置按钮
        mybtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //输入框内容置为空
                myedit1.setText("");
                myedit2.setText("");

            }
        });
    }
}
