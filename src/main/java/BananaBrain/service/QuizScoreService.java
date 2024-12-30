package BananaBrain.service;

import BananaBrain.model.MyAppUser;
import BananaBrain.model.QuizScore;
import BananaBrain.repository.QuizScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizScoreService {
    private final QuizScoreRepository quizScoreRepository;

    @Transactional
    public QuizScore saveScore(MyAppUser user, int score, String category, String difficulty, int totalQuestions) {
        QuizScore quizScore = QuizScore.builder()
                .user(user)
                .score(score)
                .category(category)
                .difficulty(difficulty)
                .totalQuestions(totalQuestions)
                .completedAt(LocalDateTime.now())
                .build();
        return quizScoreRepository.save(quizScore);
    }

    public List<QuizScore> getLeaderboard() {
        return quizScoreRepository.findAllByOrderByScoreDescCompletedAtDesc();
    }

    public List<QuizScore> getUserScores(MyAppUser user) {
        return quizScoreRepository.findByUserOrderByCompletedAtDesc(user);
    }

    public List<QuizScore> getLeaderboardByDifficulty(String difficulty) {
        return quizScoreRepository.findByDifficultyOrderByScoreDescCompletedAtDesc(difficulty);
    }

    public List<QuizScore> getLeaderboardByCategory(String category) {
        return quizScoreRepository.findByCategoryOrderByScoreDescCompletedAtDesc(category);
    }
}
