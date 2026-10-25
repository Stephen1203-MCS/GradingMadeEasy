package com.example.gradingmadeeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gradingmadeeasy.Pena_SectionFolder.Section;
import com.example.gradingmadeeasy.Pena_SectionFolder.SectionTemplate;
import com.example.gradingmadeeasy.Pena_SectionFolder.SectionRepository;

public class SectionCreation extends AppCompatActivity {
    private EditText sectionNameInput;
    private RadioGroup gradingSystemGroup;
    private TextView formulaTextBox;
    private Button createSectionButton;
    private Button exitButton;

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

        bindViews();
        setupGradingSystemChoice();
        setupButtons();
    }

    private void bindViews() { // connect the variables to the sectionName
        sectionNameInput = findViewById(R.id.sectionName);
        gradingSystemGroup = findViewById(R.id.radioGroupForGradingSystems);
        formulaTextBox = findViewById(R.id.formulaTextBox);
        createSectionButton = findViewById(R.id.createSectionDetailsButton);
        exitButton = findViewById(R.id.exitButtonForSectionCreation);
    }

    // ---------- Grading system choice ----------
    private void setupGradingSystemChoice() {
        // Changing the choice updates the formula box.
        gradingSystemGroup.setOnCheckedChangeListener((group, checkedId) -> showFormula());

        // Start with Weighted Category selected. This also triggers the listener above.
        gradingSystemGroup.check(R.id.radioButton_WeightedCategory);
    }

    // The value that gets saved in the Section.
    private String getSelectedGradingSystem() {
        if (gradingSystemGroup.getCheckedRadioButtonId() == R.id.radioButton_SumOfAllScores) {
            return Section.GRADING_TOTAL;
        }
        return Section.GRADING_WEIGHTED;
    }

    private void showFormula() {
        if (Section.GRADING_TOTAL.equals(getSelectedGradingSystem())) {
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

    // ---------- Buttons ----------
    private void setupButtons() {
        exitButton.setOnClickListener(v -> finish());
        createSectionButton.setOnClickListener(v -> createSection());
    }

    // Checks the name, then opens the section screen with the name and grading system
    private void createSection() {
        String name = sectionNameInput.getText().toString().trim();
        if (name.isEmpty()) {
            sectionNameInput.setError("Enter a section name");
            sectionNameInput.requestFocus();
            return;
        }

        Section section = new Section(name, getSelectedGradingSystem());
        SectionRepository.getInstance().addSection(section);

        Intent intent = new Intent(this, SectionTemplate.class);
        intent.putExtra(SectionTemplate.EXTRA_SECTION_ID, section.getId());
        startActivity(intent);
        finish();
    }
}