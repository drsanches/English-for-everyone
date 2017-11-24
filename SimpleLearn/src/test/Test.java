package test;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
import java.util.Scanner;


public class Test {

    public static void getTest(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Type: ");
            String type = (new Scanner(System.in)).nextLine();
            serverGetTest(sessionId, type);
        } else if (args.length == 2) {
            String sessionId = args[0];
            String type = args[1];
            serverGetTest(sessionId, type);
        } else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetTest(String sessionId, String type) {
        try {
            String url = ServerAddress.getAddress("gettest.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId
                            + "&Type=" + type)
                    .asJson();

            printGetTestResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printGetTestResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);


            if (status.equals("Success")) {
                System.out.println("Test id: " + response.getInt("TestId"));
                JSONArray questions = response.getJSONArray("Questions");

                for (int i = 0; i < questions.length(); i++) {
                    JSONObject question = questions.getJSONObject(i);
                    System.out.println("Question" + Integer.toString(i + 1) + ": ");
                    System.out.println("    Question: " + question.getString("Question"));

                    JSONArray answers = question.getJSONArray("Answers");
                    for (int j = 0; j < answers.length(); j++)
                        System.out.println("    " + Integer.toString(j + 1) + ". " + answers.getString(j) + ";");
                    System.out.println();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void gradeTestResults(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Test id: ");
            int testId = Integer.parseInt((new Scanner(System.in)).nextLine());
            System.out.print("Count of answers: ");
            int countOfAnswers = Integer.parseInt((new Scanner(System.in)).nextLine());
            String[] answers = new String[countOfAnswers];

            for (int i = 0; i < countOfAnswers; i++) {
                System.out.print("Answer " + Integer.toString(i + 1) + ": ");
                answers[i] = (new Scanner(System.in)).nextLine();
            }

            serverGradeTestResults(sessionId, testId, answers);
        } else if (args.length > 2) {
            String sessionId = args[0];
            int testId = Integer.parseInt(args[1]);
            String[] answers = new String[args.length - 2];

            for (int i = 0; i < answers.length; i++)
                answers[i] = args[i + 2];

            serverGradeTestResults(sessionId, testId, answers);
        } else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGradeTestResults(String sessionId, int testId, String[] answers) {
        String strAnswers = "";
        for (int i = 0; i < answers.length; i++)
            strAnswers += "&Answer-" + Integer.toString(i + 1) + "=" + answers[i];

        try {
            String url = ServerAddress.getAddress("gradetestresults.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId
                            + "&TestId=" + testId
                            + strAnswers)
                    .asJson();

            printGradeTestResultsResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printGradeTestResultsResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);

            if (status.equals("Success")) {
                System.out.println("Right answers: ");
                JSONArray rightAnswers = response.getJSONArray("RightAnswers");

                for (int i = 0; i < rightAnswers.length(); i++)
                    System.out.println(Integer.toString(i + 1) + ". " + rightAnswers.getString(i));
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
