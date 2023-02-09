package com.example.assessment3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assessment3.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Registration");

        if(gender != null && gender != "") {
            binding.textViewSelectedGender.setText(gender);
        }

        binding.buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToSetGender();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();

                if (name == null || name == "" || name.isEmpty()) {
                    Toast.makeText(getActivity(), "Name is required.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gender == null || gender == "") {
                    Toast.makeText(getActivity(), "Gender is required.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Profile profile = new Profile(name, gender);
                    mListener.goToProfile(profile);
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (RegistrationFragmentListener) context;
    }

    String gender = null;

    public void setGender(String gender) {
        this.gender = gender;
    }

    FragmentRegistrationBinding binding;

    RegistrationFragmentListener mListener;

    interface RegistrationFragmentListener {
        void goToSetGender();
        void goToProfile(Profile profile);
    }
}