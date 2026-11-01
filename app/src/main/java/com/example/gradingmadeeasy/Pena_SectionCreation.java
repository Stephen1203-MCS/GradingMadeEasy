package com.example.gradingmadeeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gradingmadeeasy.Pena_SectionFolder.SectionTemplate;

public class Pena_SectionCreation extends AppCompatActivity {

    // Initialized variables
    private Button exitButton;
    private EditText sectionNameInput;
    private RadioGroup gradingSystemGroup;
    private TextView formulaTextBox;
    private Button createSectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pena_section_creation);

        linkViews();
        setListeners();
    }

    private void linkViews() {
        exitButton = findViewById(R.id.exitButtonForSectionCreation);
        sectionNameInput = findViewById(R.id.sectionName);
        gradingSystemGroup = findViewById(R.id.radioGroupForGradingSystems);
        formulaTextBox = findViewById(R.id.formulaTextBox);
        createSectionButton = findViewById(R.id.createSectionDetailsButton);
    }

    private void setListeners() {
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(Pena_SectionCreation.this, Dashboard.class);
            startActivity(intent);
            finish();
        });

        createSectionButton.setOnClickListener(v -> {
            Intent intent = new Intent(Pena_SectionCreation.this, SectionTemplate.class);
            startActivity(intent);
            finish();
        });

        gradingSystemGroup.setOnCheckedChangeListener((group, checkedId) -> {
        });
    }
}
