package com.nct.trenx.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String goals;
    private String gender;
    private String fitnessLevel;
    private int height;
    private int weight;
    private int weightGoal;
    private String maxPushups;
    private String maxPullups;
    private String maxDips;
    private String maxSquats;

    public User() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGoals() { return goals; }
    public void setGoals(String goals) { this.goals = goals; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFitnessLevel() { return fitnessLevel; }
    public void setFitnessLevel(String fitnessLevel) { this.fitnessLevel = fitnessLevel; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getWeightGoal() { return weightGoal; }
    public void setWeightGoal(int weightGoal) { this.weightGoal = weightGoal; }

    public String getMaxPushups() { return maxPushups; }
    public void setMaxPushups(String maxPushups) { this.maxPushups = maxPushups; }

    public String getMaxPullups() { return maxPullups; }
    public void setMaxPullups(String maxPullups) { this.maxPullups = maxPullups; }

    public String getMaxDips() { return maxDips; }
    public void setMaxDips(String maxDips) { this.maxDips = maxDips; }

    public String getMaxSquats() { return maxSquats; }
    public void setMaxSquats(String maxSquats) { this.maxSquats = maxSquats; }
}
