### Use Case - View Leaderboard

| **Field**            | **Description**                                                                                                                                                                           |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Use Case ID**      | UC-04                                                                                                                                                                                     |
| **Use Case Name**    | View Leaderboard                                                                                                                                                                          |
| **Actors**           | - **Main Actor**: User<br>- **Secondary Actor**: System                                                                                                                                   |
| **Preconditions**    | - User has a registered account.<br>- There are users in the system with recorded points.                                                                                                 |
| **Main Flow**        | 1. User navigates to the leaderboard section.<br>2. The system fetches the leaderboard data, including top 3 students with the highest points.<br>3. The system displays the leaderboard. |
| **Alternative Flow** | - **3a**: If there are less than 3 students with points, the leaderboard will display: "Not enough students with banana points!"                                                          |
| **Postconditions**   | - The user can see the leaderboard.<br>- The user can collect a badge if they are number 1 on the site.                                                                                   |

---