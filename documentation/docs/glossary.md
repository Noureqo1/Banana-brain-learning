## Revision History

| **Version** | **Date**   | **Author**      | **Changes**                                                                                            | **Reviewer** |
|-------------|------------|-----------------|--------------------------------------------------------------------------------------------------------|--------------|
| 1.0         | 2024-12-27 | Mohammed Moataz | Initial draft of functionalities glossary including format, definition, validation rules, and aliases. | Dr. Doaa     |
| 1.1         | 2024-12-28 | Mohammed Moataz | Updated glossary terms for consistency and added examples to validation rules.                         | Eng. Aya     |
| 1.2         | 2025-01-30 | Mohammed Moataz | Added revision history section and ensured alignment with project requirements.                        | Dr. Doaa     |

## Glossary of Functionalities

| **Term**          | **Format**          | **Definition and Info**                                                                     | **Validation Rules**                                                            | **Aliases**        |
|-------------------|---------------------|---------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|--------------------|
| **Course**        | Text (String)       | A specific subject or topic that users can view, enroll in, and study.                      | Must have a unique name and valid details (e.g., teacher, price).               | Subject, Module    |
| **Question Bank** | List (Objects)      | A collection of questions related to topics for users to practice and test their knowledge. | Questions must be assigned to topics and have correct answers.                  | Question Pool      |
| **Answers**       | Text (String)       | Correct solutions or explanations provided for questions answered by the user.              | Answers must align with the associated question ID.                             | Solutions          |
| **Quizzes**       | List (Objects)      | Assessments within a course to test the user's understanding of the topics.                 | Must have valid questions and scoring rules.                                    | Tests, Evaluations |
| **Grades**        | Numeric (Integer)   | Scores or marks users receive after completing quizzes.                                     | Must be within valid score ranges and associated with a specific user and quiz. | Scores, Marks      |
| **Leaderboards**  | List (Ranked Users) | Rankings displaying users with the highest points based on quiz performance.                | Rankings must update dynamically based on user scores.                          | Rankings           |

