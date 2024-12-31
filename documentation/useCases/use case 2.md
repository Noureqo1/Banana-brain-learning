### Use Case - Enroll in a Course

| **Field**            | **Description**                                                                                                                                                                                                                     |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Use Case ID**      | UC-02                                                                                                                                                                                                                               |
| **Use Case Name**    | Enroll in a Course                                                                                                                                                                                                                  |
| **Actors**           | - **Main Actor**: User<br>- **Secondary Actor**: System                                                                                                                                                                             |
| **Preconditions**    | - User has a registered account.<br>- User is logged in.                                                                                                                                                                            |
| **Main Flow**        | 1. User selects a course from the home menu.<br>2. User clicks the "Register Course" button.<br>3. System displays a confirmation message.<br>4. User clicks the "Confirm" button.<br>5. User is routed to the course content page. |
| **Alternative Flow** | - **2a**: If the course has prerequisites and the user hasn’t completed them, the system displays a message indicating the prior course must be completed first.                                                                    |
| **Postconditions**   | - User is successfully enrolled in the course.<br>- User can view the course content.                                                                                                                                               |

---