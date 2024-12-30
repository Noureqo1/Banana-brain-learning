package BananaBrain.repository;

import BananaBrain.model.MyAppUser;
import BananaBrain.model.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore, Long> {
    List<QuizScore> findByUserOrderByCompletedAtDesc(MyAppUser user);
    List<QuizScore> findAllByOrderByScoreDescCompletedAtDesc();
    List<QuizScore> findByDifficultyOrderByScoreDescCompletedAtDesc(String difficulty);
    List<QuizScore> findByCategoryOrderByScoreDescCompletedAtDesc(String category);
}
