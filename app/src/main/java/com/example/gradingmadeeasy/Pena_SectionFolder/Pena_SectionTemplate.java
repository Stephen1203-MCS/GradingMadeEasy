package com.example.gradingmadeeasy.Pena_SectionFolder;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gradingmadeeasy.R;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;
import android.text.InputType;

public class Pena_SectionTemplate extends AppCompatActivity {

    // The data Section Creation sends to this screen.
    public static final String EXTRA_SECTION_NAME = "section_name";
    public static final String EXTRA_GRADING_SYSTEM = "grading_system";

    // How many fixed buttons sit after the items in each row:
    // quarters [+], categories [Grades] [+], works [+]
    private static final int QUARTER_TAIL = 1;
    private static final int CATEGORY_TAIL  = 2;
    private static final int WORK_TAIL = 1;

    private static final int COLOR_NORMAL = Color.parseColor("#576D54");
    private static final int COLOR_SELECTED = Color.parseColor("#839D7A");
    private static final int COLOR_ROW = Color.parseColor("#1E271B");

    // ---------- Views (linked in linkViews) ----------
    private Button exitButton;
    private Button updateGradesButton;
    private TextView sectionLabel;
    private TextView gradingSystemLabel;

    private LinearLayout quarterRow;
    private Button removeQuarterButton;
    private Button quarterTemplate;
    private Button addQuarterButton;

    private LinearLayout categoryRow;
    private Button removeCategoryButton;
    private Button categoryTemplate;
    private Button gradesButton;
    private Button addCategoryButton;

    private LinearLayout workRow;
    private Button removeWorkButton;
    private Button workTemplate;
    private Button addWorkButton;

    private LinearLayout studentListLayout;
    private LinearLayout studentInputRow;
    private EditText studentNameInput;
    private EditText studentScoreInput;
    private Button addStudentButton;
    private Button removeStudentButton;

    // Size and spacing copied from the XML, so new buttons and rows look the same
    private LinearLayout.LayoutParams quarterParams;
    private LinearLayout.LayoutParams categoryParams;
    private LinearLayout.LayoutParams workParams;
    private LinearLayout.LayoutParams studentRowParams;
    private LinearLayout.LayoutParams studentNameParams;
    private LinearLayout.LayoutParams studentScoreParams;

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

        linkViews();
        setListeners();
        showSectionInfo();

        // Start with the first quarter, category and work selected
        highlight(quarterRow, QUARTER_TAIL, quarterTemplate);
        highlight(categoryRow, CATEGORY_TAIL, categoryTemplate);
        highlight(workRow, WORK_TAIL, workTemplate);
    }

    // ---------- Setup ----------

    // Links every XML id to its Java variable
    private void linkViews() {
        exitButton = findViewById(R.id.exit);
        updateGradesButton = findViewById(R.id.finalizeGradeButton);
        sectionLabel = findViewById(R.id.sectionInformationBottom_Section);
        gradingSystemLabel = findViewById(R.id.sectionInformation_GradingSystemInformation);

        quarterRow = findViewById(R.id.linearLayoutForQuarters);
        removeQuarterButton = findViewById(R.id.RemoveQuarter);
        quarterTemplate = findViewById(R.id.quarter1Button);
        addQuarterButton = findViewById(R.id.addQuarter);

        categoryRow = findViewById(R.id.linearLayoutForCategories);
        removeCategoryButton = findViewById(R.id.removeCategory);
        categoryTemplate = findViewById(R.id.currentCategory);
        gradesButton = findViewById(R.id.finalGradeButton);
        addCategoryButton = findViewById(R.id.addCategory);

        workRow = findViewById(R.id.linearLayoutForWorkholder);
        removeWorkButton = findViewById(R.id.RemoveWork);
        workTemplate = findViewById(R.id.work1);
        addWorkButton = findViewById(R.id.addWork);

        studentListLayout = findViewById(R.id.layoutForListOfStudents);
        studentInputRow = findViewById(R.id.StudentInformation);
        studentNameInput = findViewById(R.id.studentNameInput);
        studentScoreInput = findViewById(R.id.studentScoreInput);
        addStudentButton = findViewById(R.id.addStudentInformation);
        removeStudentButton = findViewById(R.id.removeStudentInformation);

        quarterParams = copyParams(quarterTemplate);
        categoryParams = copyParams(categoryTemplate);
        workParams = copyParams(workTemplate);
        studentRowParams = copyParams(studentInputRow);
        studentNameParams = copyParams(studentNameInput);
        studentScoreParams = copyParams(studentScoreInput);
    }

    private LinearLayout.LayoutParams copyParams(View view) {
        return new LinearLayout.LayoutParams((LinearLayout.LayoutParams) view.getLayoutParams());
    }

    // Every button gets its action here
    private void setListeners() {
        exitButton.setOnClickListener(v -> goToDashboard());
        updateGradesButton.setOnClickListener(v -> updateGrades());

        quarterTemplate.setOnClickListener(v -> highlight(quarterRow, QUARTER_TAIL, quarterTemplate));
        addQuarterButton.setOnClickListener(v -> addQuarter());
        removeQuarterButton.setOnClickListener(v -> removeLastItem(quarterRow, QUARTER_TAIL, "quarter"));

        categoryTemplate.setOnClickListener(v -> highlight(categoryRow, CATEGORY_TAIL, categoryTemplate));
        addCategoryButton.setOnClickListener(v -> addCategory());
        removeCategoryButton.setOnClickListener(v -> removeLastItem(categoryRow, CATEGORY_TAIL, "category"));
        gradesButton.setOnClickListener(v -> showGrades());

        workTemplate.setOnClickListener(v -> highlight(workRow, WORK_TAIL, workTemplate));
        addWorkButton.setOnClickListener(v -> addWork());
        removeWorkButton.setOnClickListener(v -> removeLastItem(workRow, WORK_TAIL, "work"));

        addStudentButton.setOnClickListener(v -> addStudent());
        removeStudentButton.setOnClickListener(v -> removeLastStudent());
    }

    // Close screen, assuming that the dashboard doesn't finish itself (idk cuz I haven't seen espinosa's part yet)
    private void goToDashboard() {
        finish();
    }

    // Shows the section name and grading system that were sent in
    private void showSectionInfo() {
        String name = getIntent().getStringExtra(EXTRA_SECTION_NAME);
        String gradingSystem = getIntent().getStringExtra(EXTRA_GRADING_SYSTEM);
        if (name == null) {
            name = "New Section";
        }
        if (gradingSystem == null) {
            gradingSystem = "Weighted Category";
        }

        sectionLabel.setText("Current Section: " + name);
        gradingSystemLabel.setText("Grading System: " + gradingSystem);
    }

    // ---------- Helpers ----------

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Shows 85 instead of 85.0, but keeps 85.5
    private String formatScore(double score) {
        if (score == Math.floor(score)) {
            return String.valueOf((long) score);
        }
        return String.valueOf(score);
    }

    // ---------- Quarter, category and work rows ----------

    // Asks for a name. The dialog stays open if the name is empty
    // What to do with the name the user typed
    private interface NameAction {
        void onName(String name);
    }

    // Asks for a name. The dialog stays open if the name is empty.
    private void showNameDialog(String title, String currentName, final NameAction action) {
        final EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setText(currentName);
        input.selectAll();

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        // The Save click is set after show() so an empty name keeps the dialog open.
        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String name = input.getText().toString().trim();
                    if (name.isEmpty()) {
                        input.setError("Enter a name");
                        return;
                    }
                    action.onName(name);
                    dialog.dismiss();
                }));
        dialog.show();
    }

    // Smaller text so names fit on the button
    private void smallText(Button button) {
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        button.setMaxLines(2);
        button.setPadding(4, 0, 4, 0);
    }

    // Long-pressing the button asks for a new name
    private void makeRenameable(final Button button, final String title) {
        button.setOnLongClickListener(v -> {
            showNameDialog(title, button.getText().toString(), name -> button.setText(name));
            return true;
        });
    }

    private int itemCount(LinearLayout row, int tail) {
        return row.getChildCount() - 1 - tail;
    }

    // Makes the given item look selected and every other item in the row look normal
    private void highlight(LinearLayout row, int tail, Button selected) {
        for (int i = 1; i <= itemCount(row, tail); i++) {
            Button item = (Button) row.getChildAt(i);
            item.setBackgroundTintList(
                    ColorStateList.valueOf(item == selected ? COLOR_SELECTED : COLOR_NORMAL));
        }
    }

    // Adds a new item just before the fixed buttons at the end of the row, and selects it
    private MaterialButton addItem(final LinearLayout row, final int tail, String text,
                                   LinearLayout.LayoutParams params) {
        final MaterialButton button = new MaterialButton(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(params));
        button.setInsetTop(0);
        button.setInsetBottom(0);
        button.setText(text);
        button.setOnClickListener(v -> highlight(row, tail, button));

        row.addView(button, row.getChildCount() - tail);
        highlight(row, tail, button);

        if (row.getParent() instanceof HorizontalScrollView) {
            final HorizontalScrollView hsv = (HorizontalScrollView) row.getParent();
            hsv.post(() -> hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT));
        }

        return button;
    }

    // Removes the last item in the row, but always keeps at least one
    private void removeLastItem(LinearLayout row, int tail, String itemName) {
        int count = itemCount(row, tail);
        if (count <= 1) {
            toast("You need at least one " + itemName);
            return;
        }

        row.removeViewAt(count); // the last item sits at position "count"
        highlight(row, tail, (Button) row.getChildAt(count - 1)); // select the new last item
    }

    private void addQuarter() {
        addItem(quarterRow, QUARTER_TAIL,
                String.valueOf(itemCount(quarterRow, QUARTER_TAIL) + 1), quarterParams);
    }

    // What to do with the name and weight the user typed.
    private interface CategoryAction {
        void onSave(String name, double weight);
    }

    // Asks for a category name and weight. The dialog stays open until both are valid
    private void showCategoryDialog(String title, String currentName, String currentWeight,
                                    final CategoryAction action) {
        final EditText nameInput = new EditText(this);
        nameInput.setHint("Category name");
        nameInput.setSingleLine(true);
        nameInput.setText(currentName);

        final EditText weightInput = new EditText(this);
        weightInput.setHint("Weight (%)");
        weightInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        weightInput.setText(currentWeight);

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(20), dp(10), dp(20), 0);
        form.addView(nameInput);
        form.addView(weightInput);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        // The Save click is set after show() so invalid input keeps the dialog open.
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
                        weightInput.setError("Enter a weight");
                        return;
                    }
                    if (weight > 100) {
                        weightInput.setError("Use 0 to 100");
                        return;
                    }

                    action.onSave(name, weight);
                    dialog.dismiss();
                }));
        dialog.show();
    }

    // A category button shows its name and weight, and keeps the weight in its tag.
    private void setCategory(Button button, String name, double weight) {
        button.setTag(weight);
        button.setText(name + "\n" + formatScore(weight) + "%");
    }

    private String getCategoryName(Button button) {
        return button.getText().toString().split("\n")[0];
    }

    private double getCategoryWeight(Button button) {
        return (Double) button.getTag();
    }

    // Long-pressing a category button edits its name and weight
    private void makeCategoryEditable(final Button button) {
        button.setOnLongClickListener(v -> {
            showCategoryDialog("Edit category", getCategoryName(button),
                    formatScore(getCategoryWeight(button)),
                    (name, weight) -> setCategory(button, name, weight));
            return true;
        });
    }

    private void addCategory() {
        String defaultName = "Category " + (itemCount(categoryRow, CATEGORY_TAIL) + 1);
        showCategoryDialog("Add category", defaultName, "", (name, weight) -> {
            MaterialButton button = addItem(categoryRow, CATEGORY_TAIL, "", categoryParams);
            smallText(button);
            setCategory(button, name, weight);
            makeCategoryEditable(button);
        });
    }

    private void addWork() {
        String defaultName = "Work " + (itemCount(workRow, WORK_TAIL) + 1);
        showNameDialog("Add work", defaultName, name -> {
            MaterialButton button = addItem(workRow, WORK_TAIL, name, workParams);
            smallText(button);
            makeRenameable(button, "Rename work");
        });
    }

    // ---------- Students ----------

    // Number of student rows. They sit after the "-" student button
    private int studentCount() {
        return studentListLayout.getChildCount() - (studentListLayout.indexOfChild(removeStudentButton) + 1);
    }

    private LinearLayout getStudentRow(int position) {
        int index = studentListLayout.indexOfChild(removeStudentButton) + 1 + position;
        return (LinearLayout) studentListLayout.getChildAt(index);
    }

    // The name and score are the two text boxes inside a student row
    private String getRowName(LinearLayout row) {
        return ((TextView) row.getChildAt(0)).getText().toString();
    }

    private double getRowScore(LinearLayout row) {
        return Double.parseDouble(((TextView) row.getChildAt(1)).getText().toString());
    }

    // adds a student to the list
    private void addStudent() {
        String name = studentNameInput.getText().toString().trim();
        if (name.isEmpty()) {
            studentNameInput.setError("Enter a name");
            return;
        }

        double score;
        try {
            score = Double.parseDouble(studentScoreInput.getText().toString().trim());
        } catch (NumberFormatException e) {
            studentScoreInput.setError("Enter a score");
            return;
        }

        studentListLayout.addView(makeStudentRow(name, formatScore(score)));

        if (studentListLayout.getParent() instanceof ScrollView) {
            final ScrollView sv = (ScrollView) studentListLayout.getParent();
            sv.post(() -> sv.fullScroll(ScrollView.FOCUS_DOWN));
        }

        studentNameInput.setText("");
        studentScoreInput.setText("");
        studentNameInput.requestFocus();
    }

    // Removes the last student row
    private void removeLastStudent() {
        if (studentCount() == 0) {
            toast("No students to remove");
            return;
        }
        studentListLayout.removeViewAt(studentListLayout.getChildCount() - 1);
    }

    private LinearLayout makeStudentRow(String name, String score) {
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(new LinearLayout.LayoutParams(studentRowParams));
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.addView(makeStudentCell(name, studentNameParams));
        row.addView(makeStudentCell(score, studentScoreParams));
        return row;
    }

    private TextView makeStudentCell(String text, LinearLayout.LayoutParams params) {
        TextView cell = new TextView(this);
        cell.setLayoutParams(new LinearLayout.LayoutParams(params));
        cell.setBackgroundResource(R.drawable.rounded_bg);
        cell.setBackgroundTintList(ColorStateList.valueOf(COLOR_ROW));
        cell.setTextColor(Color.WHITE);
        cell.setGravity(Gravity.CENTER);
        cell.setPadding(dp(8), 0, dp(8), 0);
        cell.setText(text);
        return cell;
    }

    // ---------- Grades ----------

    // Average of the scores currently in the list
    private double calculateAverage() {
        double total = 0;
        for (int i = 0; i < studentCount(); i++) {
            total += getRowScore(getStudentRow(i));
        }
        return total / studentCount();
    }

    // "Update Grades" recalculates the class average from the scores in the list.
    private void updateGrades() {
        if (studentCount() == 0) {
            toast("Add a student first");
            return;
        }
        toast("Grades updated. Class average: " + String.format(Locale.US, "%.2f", calculateAverage()));
    }

    // "Grades" shows every student's score and the class average.
    private void showGrades() {
        if (studentCount() == 0) {
            toast("Add a student first");
            return;
        }

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < studentCount(); i++) {
            LinearLayout row = getStudentRow(i);
            summary.append(getRowName(row)).append(": ").append(formatScore(getRowScore(row))).append("\n");
        }
        summary.append("\nClass average: ").append(String.format(Locale.US, "%.2f", calculateAverage()));

        new AlertDialog.Builder(this)
                .setTitle("Grades")
                .setMessage(summary.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}