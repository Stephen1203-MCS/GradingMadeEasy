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
       /* for every section in the list returned by getSections
       compare with the id, return the section, if none return null
       * */
        return null;
    }

    public void removeSection(String id) {
       /* for every section in the list returned by getSections
          compare with id, then NUKE/Delete if it matches. Return null if none
        */
    }
}
