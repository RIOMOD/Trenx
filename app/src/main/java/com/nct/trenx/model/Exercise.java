package com.nct.trenx.model;

public class Exercise {
    private String name;
    private String reps;
    private String category;
    private String description;
    private String imageName;
    private String day; // Thêm ngày tập (Thứ 2, Thứ 3...)
    private String difficulty; // Thêm độ khó (Beginner, Intermediate, Advanced)

    public Exercise(String name, String reps, String category, String description, String imageName, String day, String difficulty) {
        this.name = name;
        this.reps = reps;
        this.category = category;
        this.description = description;
        this.imageName = imageName;
        this.day = day;
        this.difficulty = difficulty;
    }

    public String getName() { return name; }
    public String getReps() { return reps; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getImageName() { return imageName; }
    public String getDay() { return day; }
    public String getDifficulty() { return difficulty; }
}
