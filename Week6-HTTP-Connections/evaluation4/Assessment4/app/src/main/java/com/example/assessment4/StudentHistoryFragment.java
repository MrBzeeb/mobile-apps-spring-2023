package com.example.assessment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.assessment4.Models.CourseHistory;
import com.example.assessment4.Models.DataServices;
import com.example.assessment4.Models.Student;
import com.example.assessment4.databinding.FragmentStudentHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentHistoryFragment extends Fragment {

    private static final String ARG_PARAM_STUDENT = "ARG_PARAM_STUDENT";

    private Student mStudent;

    public StudentHistoryFragment() {
        // Required empty public constructor
    }

    public static StudentHistoryFragment newInstance(Student student) {
        StudentHistoryFragment fragment = new StudentHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_STUDENT, student);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable(ARG_PARAM_STUDENT);
        }
    }

    FragmentStudentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Student History");

        binding.textViewStudentName.setText(mStudent.getName());

        ArrayList<CourseHistory> courseHistory = mStudent.getCourses();

        CourseHistoryAdapter adapter = new CourseHistoryAdapter(getActivity(), courseHistory);

        binding.listView.setAdapter(adapter);
    }

    class CourseHistoryAdapter extends ArrayAdapter<CourseHistory> {
        public CourseHistoryAdapter(@NonNull Context context, @NonNull List<CourseHistory> objects) {
            super(context, R.layout.history_row_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.history_row_item, parent, false);
            }

            TextView textViewCourseHours = convertView.findViewById(R.id.textViewCourseHours);
            TextView textViewCourseName = convertView.findViewById(R.id.textViewCourseName);
            TextView textViewCourseLetterGrade = convertView.findViewById(R.id.textViewCourseLetterGrade);
            TextView textViewCourseNumber = convertView.findViewById(R.id.textViewCourseNumber);

            CourseHistory courseHistory = getItem(position);

            textViewCourseHours.setText(courseHistory.getHours().toString());
            textViewCourseName.setText(courseHistory.getName());
            textViewCourseLetterGrade.setText(courseHistory.getLetterGrade());
            textViewCourseNumber.setText(courseHistory.getNumber());

            return convertView;
        }
    }
}