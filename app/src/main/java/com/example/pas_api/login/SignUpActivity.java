package com.example.pas_api.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pas_api.ApiClientLogin;
import com.example.pas_api.ApiServiceLogin;
import com.example.pas_api.R;
import com.example.pas_api.databinding.ActivitySignUpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private String fullname, username, email, password, confirmPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = binding.edFullname.getText().toString();
                username = binding.edUsername.getText().toString();
                email = binding.edEmail.getText().toString();
                password = binding.edPassword.getText().toString();
                confirmPassword = binding.edConfirmPassword.getText().toString();

                if (formIsEmpty()) {
                    toastMessage(getString(R.string.error_field_required));
                } else if (!isUsernameValid(username)) {
                    toastMessage(getString(R.string.error_valid_username));
                    //dia harus berisi huruf besar atau kecil dan underscore dan angka
                } else if (!isEmailValid(email)) {
                    toastMessage(getString(R.string.error_valid_email));
                    //pake @
                } else if (!isMinimalPasswordValid(password) || !isMinimalPasswordValid(confirmPassword)) {
                    toastMessage(getString(R.string.error_minimal_password));
                } else if (!isPasswordMatch()) {
                    toastMessage(getString(R.string.error_password_dont_match));
                    //password harus cocok
                } else {

                    //Login Process
                    signUpProcess();
                    //kalo yang diatas sudah benar semua, kode ini dijalankan

                }
            }
        });
    }

    private void toastMessage(String value) {
        Toast.makeText(SignUpActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private boolean formIsEmpty() {
        return fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
        //OR (||) hasil akhirnya akan bernilai true jika ada salah satu kondisi yang bernilai true
        //AD hasil akhirnya akan bernilai false jika ada salah satu kondisi bernilai false, otherwise nilainya true
    }

    private boolean isUsernameValid(String value) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return pattern.matcher(value)
                .matches();
    }

    private boolean isEmailValid(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value)
                .matches();
    }

    private boolean isMinimalPasswordValid(String value) {
        return value.length() >= 8;
    }

    private boolean isPasswordMatch() {
        return password.equals(confirmPassword);
    }

    private void signUpProcess() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        ApiServiceLogin service = ApiClientLogin.getRetrofitInstance().create(ApiServiceLogin.class);
        Call<ResponseBody> call = service.registerUser(username, fullname, email, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (res != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(res);
                        Boolean status = jsonResponse.getBoolean("status");
                        String message = jsonResponse.getString("message");

                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (status == true) {
                            goToLoginActivity();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                toastMessage("Something went wrong...Please try later!");
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
