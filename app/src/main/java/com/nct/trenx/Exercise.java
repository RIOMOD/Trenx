package com.nct.trenx;

public class Exercise {
    private String name;
    private String reps;
    private String category;
    private String description;
    private String imageName; // Thêm dòng này

    public Exercise(String name, String reps, String category, String description, String imageName) {
        this.name = name;
        this.reps = reps;
        this.category = category;
        this.description = description;
        this.imageName = imageName;
    }

    public String getName() { return name; }
    public String getReps() { return reps; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getImageName() { return imageName; } // Thêm getter
}