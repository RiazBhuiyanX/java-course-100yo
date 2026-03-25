package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class NameWorkoutFilter implements WorkoutFilter {
    private final String keyword;
    private final boolean caseSensitive;

    public NameWorkoutFilter(String keyword, boolean caseSensitive) {
        if (keyword == null || keyword.isBlank()){
            throw new IllegalArgumentException("Keyword cannot be null or empty!");
        }

        this.keyword = keyword;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean matches(Workout workout){
        if (workout == null){
            return false;
        }

        String workoutName = workout.getName();

        if (caseSensitive){
            return workoutName.contains(keyword);
        } else {
            return workoutName.toLowerCase().contains(keyword.toLowerCase());
        }
    }
}
