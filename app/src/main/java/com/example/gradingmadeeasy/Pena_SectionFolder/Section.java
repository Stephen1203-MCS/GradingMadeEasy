package com.example.gradingmadeeasy.Pena_SectionFolder;

import java.util.ArrayList;
import java.util.UUID;

public class Section {
    // The two grading systems. A section uses one of them, chosen when the section is created
    // These texts are also what the teacher sees, so they match the radio buttons in Section Creation
    public static final String GRADING_WEIGHTED = "Weighted Category";
    public static final String GRADING_TOTAL = "Total Sum of Points";

    // Never changes. Used to tell sections apart and to pass a section between screens
    private final String id;

    private String name;
    private String gradingSystem;
    private ArrayList<Quarter> quarters;

    // The class list. Every work in this section shows these students
    private ArrayList<Students> students;

    public Section(String name, String gradingSystem) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.gradingSystem = gradingSystem;
        this.quarters = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradingSystem() {
        return gradingSystem;
    }

    public void setGradingSystem(String gradingSystem) {
        this.gradingSystem = gradingSystem;
    }

    public boolean isWeighted() {
        return GRADING_WEIGHTED.equals(gradingSystem);
    }

    public ArrayList<Quarter> getQuarters() {
        return quarters;
    }

    public ArrayList<Students> getStudents() {
        return students;
    }

    // Recalculates the saved grades of every quarter using this section's grading system
    public void recalculateAllGrades() {
        boolean weighted = isWeighted();
        for (Quarter quarter : quarters) {
            quarter.recalculateGrades(students, weighted);
        }
    }

    // True if another student in the class list already has this name (ignores upper/lower case)
    // Pass the student being renamed as "ignore" so they don't clash with themselves, or null
    public boolean isNameTaken(String name, Students ignore) {
        String wanted = name.trim();
        for (Students student : students) {
            if (student != ignore && student.getName().trim().equalsIgnoreCase(wanted)) {
                return true;
            }
        }
        return false;
    }

    // Removes the student from the class list and deletes their scores and grades everywhere
    public void removeStudent(Students student) {
        students.remove(student);
        for (Quarter quarter : quarters) {
            quarter.clearGrade(student);
            for (Category category : quarter.getCategories()) {
                for (Work work : category.getWorks()) {
                    work.clearScore(student);
                }
            }
        }
    }
}