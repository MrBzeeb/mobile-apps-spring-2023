package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inclass04.databinding.FragmentMainBinding;
import com.example.inclass04.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {
    String department;

    public void setDepartment(String department) {
        this.department = department;
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }

    FragmentRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("demo", "onCreateView");
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registration");

        if (department == null) {
            binding.textViewDept.setText("");
        } else {
            binding.textViewDept.setText(department);
        }

        Log.d("demo", "onViewCreated");

        binding.buttonSelectDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToDepartment();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextTextPersonName.getText().toString();
                String email = binding.editTextTextEmailAddress.getText().toString();
                String id = binding.editTextNumber.getText().toString();

                Profile profile = new Profile(name, email, id, department);

                mListener.goToProfile(profile);
            }
        });
    }

    RegistrationFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (RegistrationFragmentListener) context;
    }

    interface RegistrationFragmentListener {
        void goToDepartment();
        void goToProfile(Profile profile);
    }
}