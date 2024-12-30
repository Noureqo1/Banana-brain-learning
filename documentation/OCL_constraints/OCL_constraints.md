# OCL Constraints

## QUIZ OCL

1. **Ensure score is a non-negative integer**
    - **Context:** `QuizScore`
    - **Invariant:** `self.score >= 0`

2. **Ensure totalQuestions is greater or equal to 5**
    - **Context:** `QuizScore`
    - **Invariant:** `self.totalQuestions >= 5`

3. **Ensure that difficulty and category are not empty**
    - **Context:** `QuizScore`
    - **Invariant:** `not self.difficulty.isEmpty() and not self.category.isEmpty()`

4. **Ensure that completedAt is always in the past**
    - **Context:** `QuizScore`
    - **Invariant:** `self.completedAt < now()`

---

## LOGIN OCL

5. **Ensure username is not null or empty**
    - **Context:** `MyAppUser`
    - **Invariant:** `not self.username.isEmpty()`

6. **Ensure email is not null or empty**
    - **Context:** `MyAppUser`
    - **Invariant:** `not self.email.isEmpty()`

7. **Ensure password is not null or empty**
    - **Context:** `MyAppUser`
    - **Invariant:** `not self.password.isEmpty()`

---

## COURSES

8. **Ensure enrolledCourses do not contain null values**
    - **Context:** `MyAppUser`
    - **Invariant:** `self.enrolledCourses->forAll(enrolled courses | enrolled courses <> null)`

9. **Ensure enrolledCourses are unique**
    - **Context:** `MyAppUser`
    - **Invariant:** `self.enrolledCourses->isUnique(enrolled courses | enrolled courses.course)`

10. **Ensure Course IDs are unique**
    - **Context:** `Course`
    - **Invariant:** `Course.allInstances()->forAll(c1, c2 | c1 <> c2 implies c1.id <> c2.id)`

11. **Ensure teacher is defined and not empty**
    - **Context:** `Course`
    - **Invariant:** `not self.teacher.oclIsUndefined() and self.teacher.size() > 0`

12. **Ensure price is defined and not empty**
    - **Context:** `Course`
    - **Invariant:** `not self.price.oclIsUndefined() and self.price.size() > 0`

13. **Ensure enrolled users are unique**
    - **Context:** `Course`
    - **Invariant:** `self.enrolledUsers->forAll(eu1, eu2 | eu1 <> eu2 implies eu1.user <> eu2.user)`
