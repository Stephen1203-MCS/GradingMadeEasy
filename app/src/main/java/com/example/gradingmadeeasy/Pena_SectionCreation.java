package com.example.gradingmadeeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gradingmadeeasy.Pena_SectionFolder.Pena_SectionTemplate;

public class Pena_SectionCreation extends AppCompatActivity {

    // ---------- Views (linked in linkViews) ----------
    private Button exitButton;
    private EditText sectionNameInput;
    private RadioGroup gradingSystemGroup;
    private RadioButton totalSumRadio;
    private RadioButton weightedRadio;
    private TextView formulaTextBox;
    private Button createSectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pena_section_creation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        linkViews();
        setListeners();

        // Weighted Category is the starting choice
        weightedRadio.setChecked(true);
    }

    private void linkViews() {
        exitButton = findViewById(R.id.exitButtonForSectionCreation);
        sectionNameInput = findViewById(R.id.sectionName);
        gradingSystemGroup = findViewById(R.id.radioGroupForGradingSystems);
        totalSumRadio = findViewById(R.id.radioButton_SumOfAllScores);
        weightedRadio = findViewById(R.id.radioButton_WeightedCategory);
        formulaTextBox = findViewById(R.id.formulaTextBox);
        createSectionButton = findViewById(R.id.createSectionDetailsButton);
    }

    private void setListeners() {
        exitButton.setOnClickListener(v -> finish());
        createSectionButton.setOnClickListener(v -> createSection());

        // Picking a grading system updates the formula shown
        gradingSystemGroup.setOnCheckedChangeListener((group, checkedId) -> showFormula());
    }

    private void showFormula() {
        if (totalSumRadio.isChecked()) {
            formulaTextBox.setText(
                    "Grade = (All student scores \u00F7 All max scores) \u00D7 100\n\n"
                            + "Category weights are ignored.\n"
                            + "A missing score counts as 0.");
        } else {
            formulaTextBox.setText(
                    "Category % = (Category scores \u00F7 Category max scores) \u00D7 100\n\n"
                            + "Grade = Sum of (Category % \u00D7 Weight) \u00F7 Sum of Weights\n\n"
                            + "A missing score counts as 0.");
        }
    }

    // Checks the name, then opens the section screen with the name and grading systemm
    private void createSection() {
        String name = sectionNameInput.getText().toString().trim();
        if (name.isEmpty()) {
            sectionNameInput.setError("Enter a section name");
            sectionNameInput.requestFocus();
            return;
        }

        // The grading system is the text of the selected radio button
        RadioButton selected = totalSumRadio.isChecked() ? totalSumRadio : weightedRadio;

        Intent intent = new Intent(this, Pena_SectionTemplate.class);
        intent.putExtra(Pena_SectionTemplate.EXTRA_SECTION_NAME, name);
        intent.putExtra(Pena_SectionTemplate.EXTRA_GRADING_SYSTEM, selected.getText().toString());
        startActivity(intent);

        finish();
    }
}