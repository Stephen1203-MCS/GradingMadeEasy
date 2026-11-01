package com.example.gradingmadeeasy.Pena_SectionFolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gradingmadeeasy.Dashboard;
import com.example.gradingmadeeasy.R;

public class SectionTemplate extends AppCompatActivity {

    // Initialized variables
    private Button exitButton;
    private Button updateGradesButton;
    private TextView sectionLabel;
    private TextView gradingSystemLabel;

    private LinearLayout quarterRow;
    private Button removeQuarterButton;
    private Button addQuarterButton;

    private LinearLayout categoryRow;
    private Button removeCategoryButton;
    private Button gradesButton;
    private Button addCategoryButton;

    private TextView workLabel;
    private View workScroll;
    private LinearLayout workRow;
    private Button removeWorkButton;
    private Button addWorkButton;

    private LinearLayout studentListLayout;
    private TextView studentHeader;
    private LinearLayout studentInputRow;
    private EditText studentNameInput;
    private EditText studentScoreInput;
    private Button addStudentButton;
    private Button removeStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pena_activity_section_template);

        linkViews();
        setListeners();
    }

    private void linkViews() {
        exitButton = findViewById(R.id.exit);
        updateGradesButton = findViewById(R.id.finalizeGradeButton);
        sectionLabel = findViewById(R.id.sectionInformationBottom_Section);
        gradingSystemLabel = findViewById(R.id.sectionInformation_GradingSystemInformation);

        quarterRow = findViewById(R.id.linearLayoutForQuarters);
        removeQuarterButton = findViewById(R.id.RemoveQuarter);
        addQuarterButton = findViewById(R.id.addQuarter);

        categoryRow = findViewById(R.id.linearLayoutForCategories);
        removeCategoryButton = findViewById(R.id.removeCategory);
        gradesButton = findViewById(R.id.finalGradeButton);
        addCategoryButton = findViewById(R.id.addCategory);

        workLabel = findViewById(R.id.WorkholderTextIndicator);
        workScroll = findViewById(R.id.WorkHolderScroll);
        workRow = findViewById(R.id.linearLayoutForWorkholder);
        removeWorkButton = findViewById(R.id.RemoveWork);
        addWorkButton = findViewById(R.id.addWork);

        studentListLayout = findViewById(R.id.layoutForListOfStudents);
        studentHeader = findViewById(R.id.borderForStudentListTextHolder_STUDENT);
        studentInputRow = findViewById(R.id.StudentInformation);
        studentNameInput = findViewById(R.id.studentNameInput);
        studentScoreInput = findViewById(R.id.studentScoreInput);
        addStudentButton = findViewById(R.id.addStudentInformation);
        removeStudentButton = findViewById(R.id.removeStudentInformation);
    }

    private void setListeners() {
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(SectionTemplate.this, Dashboard.class);
            startActivity(intent);
            finish();
        });

        updateGradesButton.setOnClickListener(v -> {
        });

        addQuarterButton.setOnClickListener(v -> {
        });

        removeQuarterButton.setOnClickListener(v -> {
        });

        addCategoryButton.setOnClickListener(v -> {
        });

        removeCategoryButton.setOnClickListener(v -> {
        });

        gradesButton.setOnClickListener(v -> {
        });

        addWorkButton.setOnClickListener(v -> {
        });

        removeWorkButton.setOnClickListener(v -> {
        });

        addStudentButton.setOnClickListener(v -> {
        });

        removeStudentButton.setOnClickListener(v -> {
        });
    }
}
