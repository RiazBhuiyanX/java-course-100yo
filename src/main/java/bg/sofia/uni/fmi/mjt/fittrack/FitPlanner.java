package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;
import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutType;
import bg.sofia.uni.fmi.mjt.fittrack.workout.filter.WorkoutFilter;

import java.util.*;
import java.util.stream.Collectors;

public class FitPlanner implements FitPlannerAPI {
    private final Set<Workout> workouts;

    public FitPlanner(Collection<Workout> availableWorkouts) {
        if (availableWorkouts == null) {
            throw new IllegalArgumentException("Available workouts cannot be null");
        }
        this.workouts = Set.copyOf(availableWorkouts);
    }

    @Override
    public Set<Workout> getUnmodifiableWorkoutSet() {
        return workouts;
    }

    @Override
    public List<Workout> getWorkoutsSortedByCalories() {
        return workouts.stream()
                // Вместо Workout::getCaloriesBurned, ползваме ламбда:
                .sorted(Comparator.comparingInt((Workout w) -> w.getCaloriesBurned()).reversed())
                .toList();
    }

    @Override
    public Map<WorkoutType, List<Workout>> getWorkoutsGroupedByType() {
        return workouts.stream()
                .collect(Collectors.groupingBy(
                        w -> w.getType(),
                        Collectors.toUnmodifiableList()
                ));
    }

    @Override
    public List<Workout> findWorkoutsByFilters(List<WorkoutFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Filters cannot be null");
        }

        return workouts.stream()
                .filter(workout -> filters.stream().allMatch(filter -> filter.matches(workout)))
                .toList();
    }

    @Override
    public List<Workout> generateOptimalWeeklyPlan(int totalMinutes) throws OptimalPlanImpossibleException {
        if (totalMinutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        if (totalMinutes == 0 || workouts.isEmpty()) {
            return Collections.emptyList();
        }

        Workout[] items = workouts.toArray(new Workout[0]);
        int n = items.length;
        int[][] dp = new int[n + 1][totalMinutes + 1];

        for (int i = 1; i <= n; i++) {
            int duration = items[i - 1].getDuration();
            int calories = items[i - 1].getCaloriesBurned();
            for (int j = 0; j <= totalMinutes; j++) {
                if (duration <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], calories + dp[i - 1][j - duration]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        if (dp[n][totalMinutes] == 0) {
            throw new OptimalPlanImpossibleException("No workout fits the time limit");
        }

        List<Workout> plan = new ArrayList<>();
        int resTime = totalMinutes;
        for (int i = n; i > 0 && resTime > 0; i--) {
            if (dp[i][resTime] != dp[i - 1][resTime]) {
                plan.add(items[i - 1]);
                resTime -= items[i - 1].getDuration();
            }
        }

        return plan.stream()
                .sorted(Comparator.comparingInt((Workout w) -> w.getCaloriesBurned()).reversed()
                        .thenComparing(Comparator.comparingInt((Workout w) -> w.getDifficulty()).reversed()))
                .toList();
    }

    @Override
    public List<Workout> getWorkoutsSortedByDifficulty() {
        return workouts.stream()
                // Тук също - ламбдата казва "сравни по трудност"
                .sorted(Comparator.comparingInt(w -> w.getDifficulty()))
                .toList();
    }
}