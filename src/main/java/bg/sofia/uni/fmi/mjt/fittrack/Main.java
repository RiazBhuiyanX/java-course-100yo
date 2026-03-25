package bg.sofia.uni.fmi.mjt.fittrack; // Провери дали това е твоят пакет

import bg.sofia.uni.fmi.mjt.fittrack.workout.*;
import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        try {
            System.out.println("--- Генериране на оптимален план за 120 минути ---");
            List<Workout> plan = planner.generateOptimalWeeklyPlan(120);

            for (Workout w : plan) {
                System.out.println(w.getName() + " | Калории: " + w.getCaloriesBurned() + " | Време: " + w.getDuration() + " | Сложност: " + w.getDifficulty());
            }

        } catch (OptimalPlanImpossibleException e) {
            System.err.println("Грешка: Не може да се генерира план: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Грешка: Невалидни входни данни: " + e.getMessage());
        }
    }
}
