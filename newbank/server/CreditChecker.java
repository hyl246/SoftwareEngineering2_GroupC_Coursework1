package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CreditChecker {
  private BufferedReader bufferedReader;

  public CreditChecker() {
    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public int runCreditCheck() throws IOException {
    System.out.println("CREDIT CHECKER");

    for (String question : YesNoQuestions()) {
      if (!askAndCheckYesNoQuestion(question)) {
        System.out.println("Sorry you do not qualify for a new credit limit at this time");
        return 0;
      }
    }

    return getNewCreditLimit();
  }

  private boolean askAndCheckYesNoQuestion(String question) throws IOException {
    System.out.println(question);

    String input = bufferedReader.readLine();

    if (input.equals("y") || input.equals("Y")) {
      return true;
    }

    return false;
  }

  private int getNewCreditLimit() throws IOException {
    // TODO: error handling
    System.out.println("4. What is your monthly income (£)?");
    String input = bufferedReader.readLine();
    return Integer.parseInt(input) * 2;
  }

  private List<String> YesNoQuestions() {
    List<String> questions = new ArrayList<>();

    questions.add("1. Are you currently employed full time? (Y/N)");
    questions.add("2. Have you ever been declared bankrupt or had a foreclosure? (Y/N)");
    questions.add("3. Have you missed any payments on your current debts in the past 12 months? (Y/N)");

    return questions;
  }
}
