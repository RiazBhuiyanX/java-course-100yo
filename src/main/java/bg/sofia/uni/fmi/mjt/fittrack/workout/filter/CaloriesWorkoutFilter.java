package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class CaloriesWorkoutFilter implements WorkoutFilter {
    private final int min;
    private final int max;

    public CaloriesWorkoutFilter(int min, int max) {
        if (min > max || min < 0){
            throw new IllegalArgumentException("Min and max should be positive numbers and max should be bigger!");
        }

        this.min = min;
        this.max = max;
    }

    @Override
    public boolean matches(Workout workout) {
        if (workout == null){
            return false;
        }

        int caloriesBurned = workout.getCaloriesBurned();

        return caloriesBurned <= max && caloriesBurned >= min;
    }
}
