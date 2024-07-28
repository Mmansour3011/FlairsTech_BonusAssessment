import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.Map;

public class OrangeHRMTest {

    private static final String BASE_URL = "https://opensource-demo.orangehrmlive.com";
    private static final String LOGIN_URL = BASE_URL + "/web/index.php/auth/validate";
    private static final String CANDIDATE_API_URL = BASE_URL + "/web/index.php/api/v2/recruitment/candidates";

    public static void main(String[] args) {
        RestAssured.baseURI = BASE_URL;

        // Login and get session cookies
        Map<String, String> cookies = loginAndGetCookies("Admin", "admin123");
        if (cookies != null) {
            // Add a candidate
            int candidateId = addCandidate(cookies);

            // Delete the candidate if added successfully
            if (candidateId != -1) {
                deleteCandidate(cookies, candidateId);
            }
        }
    }

    private static Map<String, String> loginAndGetCookies(String username, String password) {
        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("txtUsername", username)
                .formParam("txtPassword", password)
                .formParam("Submit", "LOGIN");

        Response response = request.post(LOGIN_URL);

        if (response.getStatusCode() == 302) {
            return response.getCookies();
        } else {
            System.out.println("Login failed with status code: " + response.getStatusCode());
            return null;
        }
    }

    private static int addCandidate(Map<String, String> cookies) {
        RequestSpecification request = RestAssured.given()
                .cookies(cookies)
                .header("Content-Type", "application/json")
                .body(createCandidateJson());

        Response response = request.post(CANDIDATE_API_URL);

//     Print the response body
        String responseBody = response.getBody().asString();
        System.out.println("Response: " + responseBody);

        System.out.println("Add Candidate Response: " + responseBody);

        if (response.getStatusCode() == 200 && responseBody.startsWith("{")) {
            JSONObject jsonResponse = new JSONObject(responseBody);
            if (jsonResponse.has("data")) {
                JSONObject dataObject = jsonResponse.getJSONObject("data");
                if (dataObject.has("id")) {
                    System.out.println("Created Candidate ID: " + dataObject.getInt("id"));
                    return dataObject.getInt("id");
                } else {
                    System.out.println("Candidate ID not found in data object.");
                }
            } else {
                System.out.println("Data object not found in response.");
            }
        } else {
            System.out.println("Failed to add candidate or response is not in JSON format.");
        }
        return -1;
    }

    private static String createCandidateJson() {
        return new JSONObject()
                .put("firstName", "asdsad")
                .put("middleName", "sadasd")
                .put("lastName", "sadsad")
                .put("email", "dasd@asdsad.asdasd")
                .put("contactNumber", "12312412421")
                .put("keywords", JSONObject.NULL)
                .put("comment", JSONObject.NULL)
                .put("dateOfApplication", "2024-07-28")
                .put("consentToKeepData", false)
                .put("vacancyId", 5)
                .toString();
    }

    private static void deleteCandidate(Map<String, String> cookies, int candidateId) {
        RequestSpecification request = RestAssured.given()
                .cookies(cookies)
                .header("Content-Type", "application/json")
                .body(new JSONObject().put("ids", new int[]{candidateId}).toString());

        System.out.println(new JSONObject().put("ids", new int[]{candidateId}).toString());

        Response response = request.delete(CANDIDATE_API_URL);
        System.out.println("Delete Response Status Code: " + response.getStatusCode());
        System.out.println("Delete Response: " + response.getBody().asString());
    }
}

