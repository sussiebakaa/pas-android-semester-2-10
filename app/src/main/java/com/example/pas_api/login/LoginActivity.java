package com.example.pas_api.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pas_api.MainActivity;
import com.example.pas_api.R;
import com.example.pas_api.ApiClientLogin;
import com.example.pas_api.ApiServiceLogin;
import com.example.pas_api.ProfileActivity;
import com.example.pas_api.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String username, password;
    private ProgressDialog progressDialog;
    Preference preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferences = new Preference(this);
        if (preferences.getSessionLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.edUsername.getText().toString();
                password = binding.edPassword.getText().toString();

                if (formIsEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();


                } else {
                    //Login Process
                    //loginProcess();

                    if (username.equals("admin") && password.equals("admin")) {
                        preferences.setSessionLogin(true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "login sukses :)", Toast.LENGTH_SHORT);
                    } else {
                        Toast.makeText(LoginActivity.this, "login gagal:D", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }

    private boolean formIsEmpty() {
        return username.isEmpty() || password.isEmpty();
    }


    private void loginProcess() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        ApiServiceLogin service = ApiClientLogin.getRetrofitInstance().create(ApiServiceLogin.class);
        Call<ResponseBody> call = service.login(username, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                String res = null;
                try {
                    res = response.body().string(); //variabel res akan ada datanya jika berhasil, jika gagal akan dilempar
                } catch (IOException e) {
                    e.printStackTrace(); // dilempar ke sini
                }

                if (res != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(res);
                        Boolean status = jsonResponse.getBoolean("status");
                        String message = jsonResponse.getString("message");

                        toastMessage(message);

                        if (status == true) {
                            goToProfileActivity();
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

    private void goToProfileActivity() {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}
