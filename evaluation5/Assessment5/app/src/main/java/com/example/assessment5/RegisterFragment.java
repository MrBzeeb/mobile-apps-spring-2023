package com.example.assessment5;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assessment5.databinding.FragmentRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        // Required empty public constructor
    }

    FragmentRegisterBinding binding;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Register");
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoLogin();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = binding.editTextFirstName.getText().toString();
                String lname = binding.editTextLastName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //perform the registration ..
                    performRegistration(fname, lname, email, password);
                }
            }
        });
    }

    RegisterListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegisterListener) {
            mListener = (RegisterListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement RegisterListener");
        }
    }

    interface RegisterListener {
        void authSuccessful();
        void gotoLogin();
    }

    private void performRegistration(String fname, String lname, String email, String password) {
        //HttpUrl url = HttpUrl.parse("https://192.168.206.207:5555/api/signup");
        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/api/signup").newBuilder().build();
        //HttpUrl url = HttpUrl.parse("https://localhost:5555/api/signup");

        RequestBody formBody = new FormBody.Builder()
                .add("fname", fname)
                .add("lname", lname)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.w("API", "API onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.w("API", "API onResponse");
                if(response.isSuccessful()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.w("API", "authSuccessful");
                            mListener.authSuccessful();
                        }
                    });
                } else {
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String message = jsonObject.getString("message");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}