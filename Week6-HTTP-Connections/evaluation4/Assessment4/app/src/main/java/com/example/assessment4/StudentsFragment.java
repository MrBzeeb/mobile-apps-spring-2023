package com.example.assessment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.assessment4.Models.DataServices;
import com.example.assessment4.Models.Student;
import com.example.assessment4.databinding.FragmentStudentsBinding;

import java.util.ArrayList;

public class StudentsFragment extends Fragment {

    FragmentStudentsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayAdapter<String> adapter;
    ArrayList<Student> students;
    ArrayList<String> studentNames = new ArrayList<String>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Students");

        students = DataServices.getStudents();
        for(int i = 0; i < students.size(); i++) {
            studentNames.add(students.get(i).getName());
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, studentNames);

        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student selectedStudent = students.get(i);
                mListener.sendSelectedStudent(selectedStudent);
            }
        });
    }

    StudentsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (StudentsListener) context;
    }

    interface StudentsListener {
        void sendSelectedStudent(Student student);
    }
}