package com.cxyz.homepage.dto;

import java.util.List;

public class GradeLessonDto {

    private String gradeName;

    private Integer gradeId;

    private List<LessonDto> lessons;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "GradeLessonDto{" +
                "gradeName='" + gradeName + '\'' +
                ", gradeId=" + gradeId +
                ", lesson=" + lessons +
                '}';
    }
}
