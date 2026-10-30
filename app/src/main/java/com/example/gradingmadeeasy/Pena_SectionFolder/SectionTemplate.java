package com.example.gradingmadeeasy.Pena_SectionFolder;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.TextViewCompat;

import com.example.gradingmadeeasy.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;

/* HIERACHY (Summary of what I have done so far lol)
 *  Section -> Quarter -> Category -> Work
 *  in the Hierachy, they come from the files within the Section folder and from Section to Work, they hold a list.
 *  The Section ALSO holds the class list (Students). Each Work holds a score for each student (a map), not a list of students.
 *  A student who has no score in a work is just missing from that work's map.
 *  Each Quarter also holds a saved grade for each student (a map), filled when "Update Grades" is pressed. */

//Important note: THIS CANT LAUNCH BY ITSELF, YOU MAY LAUNCH THIS THROUGH SECTION CREATION


public class SectionTemplate extends AppCompatActivity {

    private static final int COLOR_NORMAL = Color.parseColor("#576D54");
    private static final int COLOR_SELECTED = Color.parseColor("#839D7A");
    private static final int COLOR_ROW = Color.parseColor("#1E271B");
    private static final int COLOR_ROW_SELECTED = Color.parseColor("#546C4C");

    // Data
    private Section section;
    private int selectedQuarter = 0;
    private int selectedCategory = 0;
    private int selectedWork = 0;
    private int selectedStudent = -1; // position in the class list, -1 means no student is selected
    private boolean gradesMode = false; // true while the "Grades" button is the active category

    // Quarter row views
    private LinearLayout quarterRow;
    private Button removeQuarterButton;
    private Button addQuarterButton;
    private LinearLayout.LayoutParams quarterButtonParams;

    // Category row views
    private LinearLayout categoryRow;
    private Button removeCategoryButton;
    private Button addCategoryButton;
    private Button gradesButton;
    private LinearLayout.LayoutParams categoryButtonParams;

    // Work row views
    private LinearLayout workRow;
    private Button removeWorkButton;
    private Button addWorkButton;
    private LinearLayout.LayoutParams workButtonParams;

    // Student views
    private LinearLayout studentListLayout;
    private EditText studentNameInput;
    private EditText studentScoreInput;
    private Button addStudentButton;
    private Button removeStudentButton;
    private LinearLayout.LayoutParams studentRowParams;
    private LinearLayout.LayoutParams studentNameCellParams;
    private LinearLayout.LayoutParams studentScoreCellParams;

    // Views that are hidden while the grades are shown
    private TextView workLabel;
    private View workScroll;
    private LinearLayout studentInputRow;
    private TextView studentHeader;
    private String studentHeaderText;

    // Bottom bar
    private Button updateGradesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pena_activity_section_template);

        // System Bar Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindViews();
        if (!loadSection()) {
            finish();
            return;
        }
        setupBottomBar();
        setupExitButton();
        setupQuarterButtons();
        setupCategoryButtons();
        setupWorkButtons();
        setupStudentButtons();
        redrawQuarters();
        redrawCategories();
        redrawWorks();
        redrawStudents();
    }

    // ---------- Setup ----------

    private void bindViews() {
        quarterRow = findViewById(R.id.linearLayoutForQuarters);
        removeQuarterButton = findViewById(R.id.RemoveQuarter);
        addQuarterButton = findViewById(R.id.addQuarter);
        quarterButtonParams = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams) addQuarterButton.getLayoutParams());

        categoryRow = findViewById(R.id.linearLayoutForCategories);
        removeCategoryButton = findViewById(R.id.removeCategory);
        addCategoryButton = findViewById(R.id.addCategory);
        gradesButton = findViewById(R.id.finalGradeButton);
        Button categoryTemplate = findViewById(R.id.currentCategory);
        categoryButtonParams = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams) categoryTemplate.getLayoutParams());

        workRow = findViewById(R.id.linearLayoutForWorkholder);
        removeWorkButton = findViewById(R.id.RemoveWork);
        addWorkButton = findViewById(R.id.addWork);
        workButtonParams = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams) addWorkButton.getLayoutParams());

        studentListLayout = findViewById(R.id.layoutForListOfStudents);
        studentNameInput = findViewById(R.id.studentNameInput);
        studentScoreInput = findViewById(R.id.studentScoreInput);
        addStudentButton = findViewById(R.id.addStudentInformation);
        removeStudentButton = findViewById(R.id.removeStudentInformation);

        // Saved student rows copy the size and spacing of the input row
        studentInputRow = findViewById(R.id.StudentInformation);
        studentRowParams = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams) studentInputRow.getLayoutParams());
        studentNameCellParams = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams) studentNameInput.getLayoutParams());
        studentScoreCellParams = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams) studentScoreInput.getLayoutParams());

        workLabel = findViewById(R.id.WorkholderTextIndicator);
        workScroll = findViewById(R.id.WorkHolderScroll);
        studentHeader = findViewById(R.id.borderForStudentListTextHolder_STUDENT);
        studentHeaderText = studentHeader.getText().toString();

        updateGradesButton = findViewById(R.id.finalizeGradeButton);
    }

    private void returnToDashBoard() {
        setContentView(R.layout.activity_dashboard);
    }

    public static final String EXTRA_SECTION_ID = "section_id";
    private boolean loadSection() {
        String id = getIntent().getStringExtra(EXTRA_SECTION_ID);
        section = (id == null) ? null : SectionRepository.getInstance().getSectionById(id);
        if (section == null) {
            return false;
        }

        if (section.getQuarters().isEmpty()) {
            section.getQuarters().add(createDefaultQuarter("Quarter 1"));
        }
        selectedQuarter = 0;
        selectedCategory = 0;
        selectedWork = 0;
        selectedStudent = -1;
        return true;
    }

    // A new quarter starts with one category and one work so students can be added right away
    private Quarter createDefaultQuarter(String name) {
        Quarter quarter = new Quarter(name);
        quarter.getCategories().add(createDefaultCategory("Performance Task", 0));
        return quarter;
    }

    private Category createDefaultCategory(String name, double weight) {
        Category category = new Category(name, weight);
        category.getWorks().add(new Work("Work 1", 100.0));
        return category;
    }

    private Quarter getCurrentQuarter() {
        return section.getQuarters().get(selectedQuarter);
    }

    private Category getCurrentCategory() {
        return getCurrentQuarter().getCategories().get(selectedCategory);
    }

    private Work getCurrentWork() {
        return getCurrentCategory().getWorks().get(selectedWork);
    }

    //Code to XML elements section
    private void setupBottomBar() {
        TextView sectionLabel = findViewById(R.id.sectionInformationBottom_Section);
        TextView gradingLabel = findViewById(R.id.sectionInformation_GradingSystemInformation);
        sectionLabel.setText("Current Section: " + section.getName());
        gradingLabel.setText("Grading System: " + section.getGradingSystem());

        updateGradesButton.setOnClickListener(v -> updateGrades());
    }

    private void setupExitButton() {
        findViewById(R.id.exit).setOnClickListener(v -> returnToDashBoard());
    }

    private void setupQuarterButtons() {
        addQuarterButton.setOnClickListener(v -> addQuarter());
        removeQuarterButton.setOnClickListener(v -> removeSelectedQuarter());
    }

    private void setupCategoryButtons() {
        addCategoryButton.setOnClickListener(v -> showCategoryDialog(null));
        removeCategoryButton.setOnClickListener(v -> removeSelectedCategory());
        gradesButton.setOnClickListener(v -> showGrades());
    }

    private void setupWorkButtons() {
        addWorkButton.setOnClickListener(v -> showWorkDialog(null));
        removeWorkButton.setOnClickListener(v -> removeSelectedWork());
    }

    private void setupStudentButtons() {
        addStudentButton.setOnClickListener(v -> addStudent());
        removeStudentButton.setOnClickListener(v -> removeSelectedStudent());
    }
    //-----------------------------

    // ---------- Shared Between Quarter, Category, Section, Students ----------

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    // Builds a button that looks like the ones in the XML (size and margins copied from a template)
    private MaterialButton makeRowButton(String text, boolean selected,
                                         LinearLayout.LayoutParams template) {
        MaterialButton button = new MaterialButton(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(template));
        button.setInsetTop(0);
        button.setInsetBottom(0);
        button.setText(text);
        button.setBackgroundTintList(
                ColorStateList.valueOf(selected ? COLOR_SELECTED : COLOR_NORMAL));
        return button;
    }

    //----------- Score format -------------
    private String formatScore(double score) {
        if (score == Math.floor(score)) {
            return String.valueOf((long) score);
        }
        return String.valueOf(score);
    }

    // Grades are shown with two decimals, like 81.71%
    private String formatGrade(double grade) {
        return String.format(Locale.US, "%.2f%%", grade);
    }

    // Returns null when the text is not a valid score
    @Nullable
    private Double parseScore(String text) {
        try {
            double value = Double.parseDouble(text.trim());
            if (value < 0) {
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    //

    // Redraws everything below the quarter row
    private void redrawBelowQuarters() {
        redrawCategories();
        redrawWorks();
        redrawStudents();
    }

    // ---------- Quarter row ----------

    private void redrawQuarters() {
        quarterRow.removeAllViews();
        quarterRow.addView(removeQuarterButton);

        for (int i = 0; i < section.getQuarters().size(); i++) {
            final int index = i;
            Button button = makeRowButton(String.valueOf(i + 1), i == selectedQuarter,
                    quarterButtonParams);
            button.setOnClickListener(v -> selectQuarter(index));
            quarterRow.addView(button);
        }

        quarterRow.addView(addQuarterButton);
    }

    // If the grades are showing, switching quarter shows that quarter's grades
    private void selectQuarter(int index) {
        selectedQuarter = index;
        selectedCategory = 0;
        selectedWork = 0;
        selectedStudent = -1;
        redrawQuarters();
        redrawBelowQuarters();
    }

    private void addQuarter() {
        int number = section.getQuarters().size() + 1;
        section.getQuarters().add(createDefaultQuarter("Quarter " + number));
        selectQuarter(section.getQuarters().size() - 1);
    }

    private void removeSelectedQuarter() {
        if (section.getQuarters().size() <= 1) {
            Toast.makeText(this, "You need at least one quarter", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Remove quarter?")
                .setMessage("This also deletes its categories, works and student scores.")
                .setPositiveButton("Remove", (dialog, which) -> {
                    section.getQuarters().remove(selectedQuarter);
                    int newIndex = Math.min(selectedQuarter, section.getQuarters().size() - 1);
                    selectQuarter(newIndex);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ---------- Category row ----------

    private void redrawCategories() {
        categoryRow.removeAllViews();
        categoryRow.addView(removeCategoryButton);

        Quarter quarter = getCurrentQuarter();
        for (int i = 0; i < quarter.getCategories().size(); i++) {
            final int index = i;
            final Category category = quarter.getCategories().get(i);

            MaterialButton button = makeRowButton(category.getName(),
                    !gradesMode && i == selectedCategory, categoryButtonParams);
            button.setPadding(4, 0, 4, 0);
            button.setMaxLines(2);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    button, 4, 12, 1, TypedValue.COMPLEX_UNIT_SP);

            button.setOnClickListener(v -> selectCategory(index));
            button.setOnLongClickListener(v -> {
                showCategoryDialog(category);
                return true;
            });
            categoryRow.addView(button);
        }

        gradesButton.setBackgroundTintList(
                ColorStateList.valueOf(gradesMode ? COLOR_SELECTED : COLOR_NORMAL));
        categoryRow.addView(gradesButton);
        categoryRow.addView(addCategoryButton);
    }

    // Selecting a category leaves the grades view.
    private void selectCategory(int index) {
        gradesMode = false;
        selectedCategory = index;
        selectedWork = 0;
        selectedStudent = -1;
        redrawCategories();
        redrawWorks();
        redrawStudents();
    }

    // existing == null means "add a new category", otherwise edit that one
    private void showCategoryDialog(final Category existing) {
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(20), dp(10), dp(20), 0);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Category name");
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText weightInput = new EditText(this);
        weightInput.setHint("Weight (%)");
        weightInput.setInputType(
                InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        if (existing != null) {
            nameInput.setText(existing.getName());
            weightInput.setText(String.valueOf(existing.getWeight()));
        }

        form.addView(nameInput);
        form.addView(weightInput);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(existing == null ? "Add category" : "Edit category")
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        // Set the Save click after show() so invalid input keeps the dialog open.
        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String name = nameInput.getText().toString().trim();
                    if (name.isEmpty()) {
                        nameInput.setError("Enter a name");
                        return;
                    }

                    double weight;
                    try {
                        weight = Double.parseDouble(weightInput.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        weightInput.setError("Enter a number");
                        return;
                    }
                    if (weight < 0 || weight > 100) {
                        weightInput.setError("Use 0 to 100");
                        return;
                    }

                    if (existing != null) {
                        existing.setName(name);
                        existing.setWeight(weight);
                    } else {
                        getCurrentQuarter().getCategories().add(createDefaultCategory(name, weight));
                        gradesMode = false;
                        selectedCategory = getCurrentQuarter().getCategories().size() - 1;
                        selectedWork = 0;
                        selectedStudent = -1;
                    }

                    redrawCategories();
                    redrawWorks();
                    redrawStudents();
                    dialog.dismiss();
                }));

        dialog.show();
    }

    private void removeSelectedCategory() {
        if (gradesMode) {
            Toast.makeText(this, "Select a category first", Toast.LENGTH_SHORT).show();
            return;
        }

        final Quarter quarter = getCurrentQuarter();
        if (quarter.getCategories().size() <= 1) {
            Toast.makeText(this, "You need at least one category", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Remove category?")
                .setMessage("This also deletes its works and student scores.")
                .setPositiveButton("Remove", (dialog, which) -> {
                    quarter.getCategories().remove(selectedCategory);
                    selectCategory(Math.min(selectedCategory, quarter.getCategories().size() - 1));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ---------- Work row ----------

    // Row order matches the XML: [-] [works...] [+]
    private void redrawWorks() {
        workRow.removeAllViews();
        workRow.addView(removeWorkButton);

        ArrayList<Work> works = getCurrentCategory().getWorks();
        for (int i = 0; i < works.size(); i++) {
            final int index = i;
            final Work work = works.get(i);
            Button button = makeRowButton(String.valueOf(i + 1), i == selectedWork,
                    workButtonParams);
            button.setOnClickListener(v -> selectWork(index));
            button.setOnLongClickListener(v -> {
                showWorkDialog(work);
                return true;
            });
            workRow.addView(button);
        }

        workRow.addView(addWorkButton);
    }

    private void selectWork(int index) {
        selectedWork = index;
        selectedStudent = -1;
        redrawWorks();
        redrawStudents();
    }

    private void showWorkDialog(final Work existing) {
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(20), dp(10), dp(20), 0);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Work name");
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText maxScoreInput = new EditText(this);
        maxScoreInput.setHint("Max Score");
        maxScoreInput.setInputType(
                InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        if (existing != null) {
            nameInput.setText(existing.getName());
            maxScoreInput.setText(formatScore(existing.getMaxScore()));
        } else {
            ArrayList<Work> works = getCurrentCategory().getWorks();
            nameInput.setText("Work " + (works.size() + 1));
            maxScoreInput.setText("100");
        }

        form.addView(nameInput);
        form.addView(maxScoreInput);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(existing == null ? "Add work" : "Edit work")
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String name = nameInput.getText().toString().trim();
                    if (name.isEmpty()) {
                        nameInput.setError("Enter a name");
                        return;
                    }

                    Double maxScore = parseScore(maxScoreInput.getText().toString());
                    if (maxScore == null || maxScore <= 0) {
                        maxScoreInput.setError("Enter a valid max score (> 0)");
                        return;
                    }

                    // Don't let the max score drop below a score that is already saved.
                    if (existing != null && existing.hasScoreAbove(maxScore)) {
                        maxScoreInput.setError("A student already has a higher score than this");
                        return;
                    }

                    if (existing != null) {
                        existing.setName(name);
                        existing.setMaxScore(maxScore);
                    } else {
                        Work work = new Work(name, maxScore);
                        getCurrentCategory().getWorks().add(work);
                        selectedWork = getCurrentCategory().getWorks().size() - 1;
                        selectedStudent = -1;
                    }

                    redrawWorks();
                    redrawStudents();
                    dialog.dismiss();
                }));

        dialog.show();
    }

    private void removeSelectedWork() {
        final ArrayList<Work> works = getCurrentCategory().getWorks();
        if (works.size() <= 1) {
            Toast.makeText(this, "You need at least one work", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Remove work?")
                .setMessage("This also deletes its student scores.")
                .setPositiveButton("Remove", (dialog, which) -> {
                    works.remove(selectedWork);
                    selectWork(Math.min(selectedWork, works.size() - 1));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ---------- Grades ----------

    // Tapping "Grades" in the category row shows the saved quarter grades of the selected quarter
    private void showGrades() {
        gradesMode = true;
        selectedStudent = -1;
        redrawCategories();
        redrawStudents();

        Quarter quarter = getCurrentQuarter();
        for (Students student : section.getStudents()) {
            if (quarter.getGrade(student) == null) {
                Toast.makeText(this, "Press Update Grades to calculate the grades",
                        Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    // "Update Grades" recalculates the saved grades of every quarter
    private void updateGrades() {
        section.recalculateAllGrades();
        redrawStudents();

        String message = "Grades updated";
        if (section.isWeighted()) {
            // Warn if a quarter's category weights don't add up to 100.
            for (int i = 0; i < section.getQuarters().size(); i++) {
                double total = section.getQuarters().get(i).getTotalWeight();
                if (Math.abs(total - 100) > 0.01) {
                    message += ". Quarter " + (i + 1) + " category weights total "
                            + formatScore(total) + "%, not 100%";
                    break;
                }
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Hides the work row and the score inputs while the grades are showing.
    private void applyGradesMode() {
        int visibility = gradesMode ? View.GONE : View.VISIBLE;
        workLabel.setVisibility(visibility);
        workScroll.setVisibility(visibility);
        studentInputRow.setVisibility(visibility);
        addStudentButton.setVisibility(visibility);
        removeStudentButton.setVisibility(visibility);
        studentHeader.setText(gradesMode
                ? studentHeaderText.replace("SCORE", "GRADE")
                : studentHeaderText);
    }

    // One read-only row: student name and their saved quarter grade
    private LinearLayout makeGradeRow(Students student, Quarter quarter) {
        Double grade = quarter.getGrade(student);
        String gradeText = (grade == null) ? "-" : formatGrade(grade);

        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(new LinearLayout.LayoutParams(studentRowParams));
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.addView(makeStudentCell(student.getName(), false, studentNameCellParams));
        row.addView(makeStudentCell(gradeText, false, studentScoreCellParams));
        return row;
    }

    // ---------- Students ----------

    // Shows the whole class list. In grades mode the second column is the quarter grade,
    // otherwise it is the student's score in the CURRENT work
    private void redrawStudents() {
        applyGradesMode();

        int firstRow = studentListLayout.indexOfChild(removeStudentButton) + 1;
        studentListLayout.removeViews(firstRow, studentListLayout.getChildCount() - firstRow);

        ArrayList<Students> classList = section.getStudents();

        if (gradesMode) {
            Quarter quarter = getCurrentQuarter();
            for (int i = 0; i < classList.size(); i++) {
                studentListLayout.addView(makeGradeRow(classList.get(i), quarter));
            }
            return;
        }

        Work currentWork = getCurrentWork();
        studentScoreInput.setHint("Score (Max " + formatScore(currentWork.getMaxScore()) + ")");

        for (int i = 0; i < classList.size(); i++) {
            studentListLayout.addView(makeStudentRow(classList.get(i), currentWork, i));
        }
    }

    private LinearLayout makeStudentRow(final Students student, final Work work, final int index) {
        boolean selected = (index == selectedStudent);

        Double score = work.getScore(student);
        String scoreText = (score == null) ? "-" : formatScore(score);

        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(new LinearLayout.LayoutParams(studentRowParams));
        row.setOrientation(LinearLayout.HORIZONTAL);

        row.addView(makeStudentCell(student.getName(), selected, studentNameCellParams));
        row.addView(makeStudentCell(scoreText, selected, studentScoreCellParams));

        // Tap: select this student and enter or change their score for this work
        row.setOnClickListener(v -> {
            selectedStudent = index;
            redrawStudents();
            showScoreDialog(student);
        });
        // Long-press: rename the student (the new name shows in every work)
        row.setOnLongClickListener(v -> {
            showRenameDialog(student);
            return true;
        });
        return row;
    }

    private TextView makeStudentCell(String text, boolean selected,
                                     LinearLayout.LayoutParams template) {
        TextView cell = new TextView(this);
        cell.setLayoutParams(new LinearLayout.LayoutParams(template));
        cell.setBackgroundResource(R.drawable.rounded_bg);
        cell.setBackgroundTintList(
                ColorStateList.valueOf(selected ? COLOR_ROW_SELECTED : COLOR_ROW));
        cell.setTextColor(Color.WHITE);
        cell.setGravity(Gravity.CENTER);
        cell.setPadding(dp(8), 0, dp(8), 0);
        cell.setText(text);
        return cell;
    }

    // Adds a student to the class list. The score is optional and only applies to the current work
    private void addStudent() {
        String name = studentNameInput.getText().toString().trim();
        if (name.isEmpty()) {
            studentNameInput.setError("Enter a name");
            return;
        }
        if (section.isNameTaken(name, null)) {
            studentNameInput.setError("Already in the class list");
            return;
        }

        Work work = getCurrentWork();
        Double score = null;
        String scoreText = studentScoreInput.getText().toString().trim();
        if (!scoreText.isEmpty()) {
            score = parseScore(scoreText);
            if (score == null) {
                studentScoreInput.setError("Enter a valid score");
                return;
            }
            if (score > work.getMaxScore()) {
                studentScoreInput.setError("Score cannot exceed max score (" + formatScore(work.getMaxScore()) + ")");
                return;
            }
        }

        Students student = new Students(name);
        section.getStudents().add(student);
        if (score != null) {
            work.setScore(student, score);
        }

        studentNameInput.setText("");
        studentScoreInput.setText("");
        studentNameInput.requestFocus();
        selectedStudent = -1;
        redrawStudents();
    }

    // Removes the selected student from the class list, which also deletes their scores in every work
    private void removeSelectedStudent() {
        if (selectedStudent < 0 || selectedStudent >= section.getStudents().size()) {
            Toast.makeText(this, "Tap a student to select, then press -",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        final Students student = section.getStudents().get(selectedStudent);
        new AlertDialog.Builder(this)
                .setTitle("Remove student?")
                .setMessage("Remove " + student.getName() + " from the class list? "
                        + "Their scores in every work will be deleted too.")
                .setPositiveButton("Remove", (dialog, which) -> {
                    section.removeStudent(student);
                    selectedStudent = -1;
                    redrawStudents();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Enter or change one student's score in the current work. Leaving it empty clears the score
    private void showScoreDialog(final Students student) {
        final Work work = getCurrentWork();

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(20), dp(10), dp(20), 0);

        final EditText scoreInput = new EditText(this);
        scoreInput.setHint("Score (Max " + formatScore(work.getMaxScore()) + ")");
        scoreInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        Double current = work.getScore(student);
        if (current != null) {
            scoreInput.setText(formatScore(current));
            scoreInput.selectAll();
        }
        form.addView(scoreInput);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Score: " + student.getName())
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        // Opens the keyboard right away so scores can be typed quickly
        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String text = scoreInput.getText().toString().trim();

                    if (text.isEmpty()) {
                        work.clearScore(student);
                    } else {
                        Double score = parseScore(text);
                        if (score == null) {
                            scoreInput.setError("Enter a valid score");
                            return;
                        }
                        if (score > work.getMaxScore()) {
                            scoreInput.setError("Score cannot exceed max score (" + formatScore(work.getMaxScore()) + ")");
                            return;
                        }
                        work.setScore(student, score);
                    }

                    redrawStudents();
                    dialog.dismiss();
                }));

        dialog.show();
    }

    // Long-press a student row to fix a typo in the name
    private void showRenameDialog(final Students student) {
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(20), dp(10), dp(20), 0);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Student name");
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nameInput.setText(student.getName());
        form.addView(nameInput);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Rename student")
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String name = nameInput.getText().toString().trim();
                    if (name.isEmpty()) {
                        nameInput.setError("Enter a name");
                        return;
                    }
                    if (section.isNameTaken(name, student)) {
                        nameInput.setError("Already in the class list");
                        return;
                    }

                    student.setName(name);
                    redrawStudents();
                    dialog.dismiss();
                }));

        dialog.show();
    }
}