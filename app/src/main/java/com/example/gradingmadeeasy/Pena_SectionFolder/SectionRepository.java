package com.example.gradingmadeeasy.Pena_SectionFolder;

import java.util.ArrayList;
import java.util.SequencedCollection;
import java.util.UUID;

//All screens will base this off of this class for their sections
public class SectionRepository {
    private static SectionRepository instance;
    private final ArrayList<Section> sections = new ArrayList<>();

    private SectionRepository() {
    }
    public static SectionRepository getInstance() {

        return null;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }


    public Section getSectionById(String id) {
        Section returnSection = null;
        for (Section section: sections) {
            if (section.getId().equals(id)) {
                returnSection = section;
            } else {
                returnSection = null;
            }
        }
        return returnSection;
    }

    public void removeSection(String id) {
        sections.removeIf(section -> section.getId().equals(id));
    }
}
