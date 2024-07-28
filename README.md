# OrangeHRM API Automation Test

## Overview

This project demonstrates how to use RestAssured for API testing with the OrangeHRM demo site. The application performs automated testing for login functionality, candidate creation, and candidate deletion through the OrangeHRM API.

## Project Structure

- `OrangeHRMTest.java`: Main class containing the test logic to:
  - Login to OrangeHRM and retrieve session cookies.
  - Add a candidate using the API.
  - Delete the candidate from the system.

## Prerequisites

- Java 11 or later
- Maven
- Internet access for connecting to the OrangeHRM demo site

## Dependencies

- [RestAssured](https://rest-assured.io/) - For making HTTP requests and handling responses
- [JSON-java](https://github.com/stleary/JSON-java) - For JSON parsing

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/Mmansour3011/FlairsTech_BonusAssessment.git
    cd FlairsTech_BonusAssessment
    ```

2. Ensure you have Maven installed. If not, download and install it from [here](https://maven.apache.org/).

3. Build the project:

    ```bash
    mvn clean install
    ```

## Configuration

No additional configuration is required. Ensure that the `OrangeHRMTest.java` file has the correct credentials and API endpoints.

## Usage

1. Run the tests:

    ```bash
    mvn exec:java -Dexec.mainClass="OrangeHRMTest"
    ```

2. The test will:
   - Log in to OrangeHRM using the provided credentials.
   - Add a new candidate.
   - Print the candidate ID.
   - Delete the candidate.
   - Print the response for both adding and deleting the candidate.

## Test Result Screenshot
![bonusAssessmentResult](https://github.com/user-attachments/assets/8d3704e4-9b08-49d3-895c-d629e4850743)

## Code Explanation

- **Login**: Sends a POST request to the login endpoint and retrieves session cookies.
- **Add Candidate**: Sends a POST request with candidate details to the API endpoint and processes the response to get the candidate ID.
- **Delete Candidate**: Sends a DELETE request to remove the candidate using the previously retrieved ID.

## Error Handling

- The script prints error messages if login fails or if responses are not in the expected format.
- Ensure to handle any potential API changes or updates on the OrangeHRM site.
