# Supplementary Specifications Template

## 1. Functionality

- **login**: authenticate the users and check the recived input is valid.

- **Quiz system**: allow users to take quizzes on courses and evaluate their performance.

- **leaderboard** : users who score top of quizzes will be displayed on the leaderboard.

- **Rewards**: Users should receive badges based on their leaderboard ranking.

## 2. Usability

### 2.1 User Interface Design
- **UI view**: text should be easy to see from 1 meter away from the mobile device
- **errors**: should be created using both sound and visuals for clear indication (not all users can see accurately).

## 3. Reliability

- **external services**: If the quiz grades gets interuppted by connection error, store it locally as a presistance measure and resend when connection is running again

## 4. Implementation Constraints

- **Technologies**: java will be the main programming language for this project and it will use spring boot.

- **Security**: User data must be stored securely, adhering to encryption standards for data at rest and in transit.

## 5. Free Open Source Components

- **Database**: Postgres is the main database of choice.

- **Version Control**: Git will be used for version control, with GitHub for repository management and team collaboration.

- **logging**: Jlog framework for any logging needs.

- **Testing**: Jest will be used for unit and integration testing to ensure high-quality code.

- **dependencies** maven will be used for handling any dependency.

- **ORM**: hibernate for object related mapping.

## 6. Interfaces

- **Rest**: CRUD operations will need to use the Rest api to for data managment.

- **database interface**: hibernate will handle communication with the database.
