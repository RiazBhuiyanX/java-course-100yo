package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.workout.*;
import bg.sofia.uni.fmi.mjt.fittrack.workout.filter.*;
import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Workout> workouts = Arrays.asList(
                new CardioWorkout("HIIT", 30, 400, 4),
                new StrengthWorkout("Upper Body", 45, 350, 3),
                new YogaSession("Morning Flow", 20, 150, 2),
                new CardioWorkout("Cycling", 60, 600, 5),
                new StrengthWorkout("Leg Day", 30, 250, 2),
                new YogaSession("Evening Relax", 15, 100, 1)
        );

        FitPlanner planner = new FitPlanner(workouts);

        System.out.println("\n--- Тренировки, сортирани по калории (max -> min) ---");
        planner.getWorkoutsSortedByCalories().forEach(w ->
                System.out.println(w.getName() + ": " + w.getCaloriesBurned() + " kcal"));

        System.out.println("\n--- Тренировки, сортирани по трудност (easy -> hard) ---");
        planner.getWorkoutsSortedByDifficulty().forEach(w ->
                System.out.println(w.getName() + ": Ниво " + w.getDifficulty()));

        System.out.println("\n--- Групиране по тип тренировка ---");
        Map<WorkoutType, List<Workout>> grouped = planner.getWorkoutsGroupedByType();
        grouped.forEach((type, list) -> {
            System.out.println(type + ": " + list.size() + " тренировки");
            list.forEach(w -> System.out.println("  - " + w.getName()));
        });


        try {
            System.out.println("\n--- Генериране на оптимален план за 120 минути ---");
            List<Workout> plan = planner.generateOptimalWeeklyPlan(120);
            plan.forEach(w -> System.out.println(w.getName() + " | " + w.getCaloriesBurned() + " kcal | " + w.getDuration() + " min |" + w.getDifficulty() + " diff"));

        } catch (OptimalPlanImpossibleException e) {
            System.err.println("Грешка: Не може да се генерира план: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Възникна неочаквана грешка: " + e.getMessage());
        }
    }
}