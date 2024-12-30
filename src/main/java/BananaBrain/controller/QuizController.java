package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.model.QuizScore;
import BananaBrain.service.QuizScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizScoreService quizScoreService;

    @GetMapping
    public String showQuizPage(Model model) {
        return "quiz";
    }

    @GetMapping("/leaderboard")
    public String showLeaderboard(Model model,
                                  @RequestParam(required = false) String difficulty,
                                  @RequestParam(required = false) String category) {
        List<QuizScore> scores;
        if (difficulty != null && !difficulty.isEmpty()) {
            scores = quizScoreService.getLeaderboardByDifficulty(difficulty);
        } else if (category != null && !category.isEmpty()) {
            scores = quizScoreService.getLeaderboardByCategory(category);
        } else {
            scores = quizScoreService.getLeaderboard();
        }
        model.addAttribute("scores", scores);
        return "leaderboard";
    }

    @PostMapping("/submit-score")
    @ResponseBody
    public ResponseEntity<?> submitScore(@RequestBody Map<String, Object> scoreData,
                                         Authentication authentication) {
        try {
            MyAppUser user = (MyAppUser) authentication.getPrincipal();
            QuizScore score = quizScoreService.saveScore(
                    user,
                    Integer.parseInt(scoreData.get("score").toString()),
                    (String) scoreData.get("category"),
                    (String) scoreData.get("difficulty"),
                    Integer.parseInt(scoreData.get("totalQuestions").toString())
            );
            return ResponseEntity.ok(Map.of("message", "Score saved successfully", "score", score));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to save score: " + e.getMessage()));
        }
    }

    @GetMapping("/my-scores")
    public String showMyScores(Model model, Authentication authentication) {
        MyAppUser user = (MyAppUser) authentication.getPrincipal();
        List<QuizScore> userScores = quizScoreService.getUserScores(user);
        model.addAttribute("scores", userScores);
        return "my-scores";
    }
}
