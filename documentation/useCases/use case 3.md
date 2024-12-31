### Use Case - Take Quizzes

| **Field**            | **Description**                                                                                                                                                                                                                    |
|----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Use Case ID**      | UC-03                                                                                                                                                                                                                              |
| **Use Case Name**    | Take Quizzes                                                                                                                                                                                                                       |
| **Actors**           | - **Main Actor**: User<br>- **Secondary Actor**: System                                                                                                                                                                            |
| **Preconditions**    | - User is enrolled in a course.<br>- Quizzes are available.                                                                                                                                                                        |
| **Main Flow**        | 1. User selects a course.<br>2. User clicks the "Start Quiz" button.<br>3. User answers all questions.<br>4. User clicks the "Submit Quiz" button.<br>5. System stores and evaluates the answers.<br>6. System displays the grade. |
| **Alternative Flow** | - **3a**: If not all questions are answered, the system displays: "Not all questions answered."                                                                                                                                    |
| **Postconditions**   | - Quiz is submitted.<br>- The grade is displayed to the user.                                                                                                                                                                      |

---