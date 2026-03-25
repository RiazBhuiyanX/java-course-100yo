package bg.sofia.uni.fmi.mjt.fittrack.workout;

import bg.sofia.uni.fmi.mjt.fittrack.exception.InvalidWorkoutException;

public final class YogaSession implements Workout{
    private final String name;
    private final int duration;
    private final int caloriesBurned;
    private final int difficulty;

    public YogaSession(String name, int duration, int caloriesBurned, int difficulty) {
        if (name == null || name.isBlank()) {
            throw new InvalidWorkoutException("Name cannot be null or blank!");
        }
        if (duration <= 0 || caloriesBurned <= 0){
            throw new InvalidWorkoutException("Duration and calories burned should be positive numbers!");
        }
        if (difficulty < 1 || difficulty > 5){
            throw new InvalidWorkoutException("Difficulty should be between 1 and 5!");
        }

        this.name = name;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.difficulty = difficulty;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public WorkoutType getType() {
        return WorkoutType.YOGA;
    }
}
