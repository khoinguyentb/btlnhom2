package com.kano.btlnhom2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kano.btlnhom2.DAO.QuanLyDAO;
import com.kano.btlnhom2.DTO.QuanLy;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtName,edtUserName,edtPassword,edtRePassword;
    private Button btn_register;
    private TextView tv_login;
    private QuanLyDAO quanLyDAO;
    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_login);
       edtName = findViewById(R.id.edt_Name_Register);
        edtPassword = findViewById(R.id.edt_Password_Register);
        edtUserName = findViewById(R.id.edt_User_Name_Register);
        edtRePassword = findViewById(R.id.edt_RePassword);
        quanLyDAO = new QuanLyDAO(this);

        tv_login.setOnClickListener(v -> {
            Intent intent1 = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent1);
        });

        btn_register.setOnClickListener(v -> {

            String name = edtName.getText().toString();
            String user = edtUserName.getText().toString();
            String pass = edtPassword.getText().toString();
            String rePass = edtRePassword.getText().toString();

            if(name.equals("")||user.equals("")||pass.equals("")||rePass.equals("")) {
                if(name.equals("")) {
                    edtName.setError("Chưa nhập tên");
                }else {
                    edtName.setError("");
                }
                if(user.equals("")) {
                    edtUserName.setError("Chưa nhập tên đăng nhập");
                }else{
                    if (quanLyDAO.checkUsername(user) == false) {
                        edtUserName.setError("");
                    }else {
                        edtUserName.setError("Tên đăng nhập đã tồn tại");
                    }
                }
                if(pass.equals("")) {
                    edtPassword.setError("Chưa nhập mật khẩu");
                }else{
                    edtPassword.setError("");
                }
                if(rePass.equals("")) {
                    edtRePassword.setError("Chưa nhập xác nhận mật khẩu");
                }else {
                    edtRePassword.setError("");
                }
            }else {
                edtName.setError("");
                edtUserName.setError("");
                edtPassword.setError("");
                edtRePassword.setError("");
                if (pass.equals(rePass)) {

                    if (quanLyDAO.checkUsername(user) == false) {

                        QuanLy obj = new QuanLy();
                        obj.setName(name);
                        obj.setId(user);
                        obj.setPassword(pass);

                        if (quanLyDAO.insert(obj) > 0) {
                            Toast.makeText(RegisterActivity.this, "Đằng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edtUserName.setError("Tên đăng nhập đã tồn tại");
                    }
                } else {
                    edtPassword.setError("");
                    edtRePassword.setError("Mật khẩu xác nhận sai");
                    if (quanLyDAO.checkUsername(user) == false) {

                        QuanLy obj = new QuanLy();
                        obj.setName(name);
                        obj.setId(user);
                        obj.setPassword(pass);

                        if (quanLyDAO.insert(obj) > 0) {
                            Toast.makeText(RegisterActivity.this, "Đằng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edtUserName.setError("Tên đăng nhập đã tồn tại");
                    }
                }

            }

        });

    }
}