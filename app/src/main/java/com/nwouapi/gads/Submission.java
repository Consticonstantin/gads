package com.nwouapi.gads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nwouapi.gads.utils.RotrifitAPI;

import org.intellij.lang.annotations.RegExp;

import java.util.List;
import java.util.regex.Pattern;
import java.lang.Object;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Submission extends AppCompatActivity {
    String firstName, lastName, email, link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Toolbar appBarLayout = findViewById(R.id.team_appBar);
        setSupportActionBar(appBarLayout);
        appBarLayout.setTitle(null);
        Button button = findViewById(R.id.submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void showConfirmDialog() {
        final Dialog dialog = new Dialog(this);
        final View v = getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        v.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        v.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAll();
                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        dialog.show();
    }

    private void verify() {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        EditText emailEdt, firstNameEdt, lastNameEdt, linkEdt;

        emailEdt = findViewById(R.id.email);
        firstNameEdt = findViewById(R.id.firstname);
        lastNameEdt = findViewById(R.id.lastName);
        linkEdt = findViewById(R.id.link);


        firstName = firstNameEdt.getText().toString().trim();
        lastName = lastNameEdt.getText().toString().trim();
        link = linkEdt.getText().toString().trim();
        email = emailEdt.getText().toString().trim();
        if (firstName.length() < 3) {
            firstNameEdt.setError("Invalid name");
            return;
        }
        if (lastName.length() < 3) {
            lastNameEdt.setError("Invalid name");
            return;
        }

        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            emailEdt.setError("Invalid email");
            return;
        }

        if (link.length() < 10) {
            linkEdt.setError("Invalid link");
            return;
        }
        showConfirmDialog();


    }

    private void showError() {
        final Dialog dialog = new Dialog(this
        );
        final  View linearLayout = getLayoutInflater().inflate(R.layout.dialog_result, null);
        TextView textView = linearLayout.findViewById(R.id.dl_text);
        textView.setText(R.string.lbl_failed);
        ImageView img =  linearLayout.findViewById(R.id.dl_img);
        img.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_warning_24));
        dialog.setContentView(linearLayout);
        dialog.show();
    }

    private void showOk() {
        final Dialog dialog = new Dialog(this
        );
        final  View linearLayout = getLayoutInflater().inflate(R.layout.dialog_result, null);
        TextView textView = linearLayout.findViewById(R.id.dl_text);
        textView.setText(R.string.lbl_succeed);
        ImageView img =  linearLayout.findViewById(R.id.dl_img);
        img.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_check_circle_24));
        dialog.setContentView(linearLayout);
        dialog.show();
    }

    private void submitAll() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RotrifitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        RotrifitAPI api = retrofit.create(RotrifitAPI.class);
        Call<Object> call = api.sendData(firstName, lastName, email, link);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object heroList = response.body();
                Log.d("submission", heroList.toString());
                showOk();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                showError();
            }
        });
    }
}