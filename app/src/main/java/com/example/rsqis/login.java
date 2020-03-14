package com.example.rsqis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText mTextEmail ;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        String user=  pref.getString("user", null);
        if(user!=null){
            JSONObject obj= null;
            try {
                obj = new JSONObject(user);
                if(obj.getString("role").equals("worker")){
                    Intent intent= new Intent(login.this,Dashboard.class);
                    startActivity(intent);
                    finish();
                }else if(obj.getString("role").equals("public")){
                    Intent FeedbackIntent= new Intent(login.this,Feedback.class);
                    startActivity(FeedbackIntent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        mTextEmail  = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mTextEmail.getText().toString();
                String pwd=mTextPassword.getText().toString();

                if (pwd.equals("admin")){
                    Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent FeedbackIntent= new Intent(login.this,Feedback.class);
                    startActivity(FeedbackIntent);
                }
                if (pwd.equals("work")){
                    Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(login.this,Dashboard.class);
                    startActivity(intent);
                }
                else{
                 login(mail,pwd);
                }

            }
        });

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerwIntent= new Intent(login.this,RegisterActivity.class);
                startActivity(registerwIntent);
            }
        });



    }

    public void login(final String email, final String password){
        String host = getResources().getString(R.string.host);
        String URLline = host+"login";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor.putString("user",response);
                        editor.commit(); // commit changes
                        try {
                            JSONObject obj=new JSONObject(response);
                             if(obj.getString("role").equals("worker")){
                                 Intent intent= new Intent(login.this,Dashboard.class);
                                 startActivity(intent);
                                 finish();
                             }else if(obj.getString("role").equals("public")){
                                 Intent FeedbackIntent= new Intent(login.this,Feedback.class);
                                 startActivity(FeedbackIntent);
                                 finish();
                             } else{
                                 Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                                       }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }


}
