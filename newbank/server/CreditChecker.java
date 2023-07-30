package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;

public class CreditChecker {
  private BufferedReader bufferedReader;

  public CreditChecker(Socket s) throws IOException {
    bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
  }

  public int runCreditCheck() throws IOException {
    System.out.println("CREDIT CHECKER");

    for (Map.Entry<String, String> question : YesNoQuestions().entrySet()) {
      if (!askAndCheckYesNoQuestion(question)) {
        return 0;
      }
    }

    return getNewCreditLimit();
  }

  private boolean askAndCheckYesNoQuestion(Map.Entry<String, String> question) throws IOException {
    System.out.println(question.getKey());

    String input = bufferedReader.readLine();
    input = input.toUpperCase();

    if (!input.equals("Y") && !input.equals("N")) {
      System.out.println("Cannot complete request at this time, check your input is correct and try again.");
      return false;
    }

    if(!input.equals(question.getValue())) {
      System.out.println("Sorry you do not qualify for a new credit limit at this time");
      return false;
    }

    return true;
  }

  private int getNewCreditLimit() throws IOException {
    // TODO: error handling
    System.out.println("4. What is your monthly income (£)?");
    String input = bufferedReader.readLine();
    return Integer.parseInt(input) * 2;
  }

  // Key is Question, Value is Correct answer
  private Map<String, String> YesNoQuestions() {
    Map<String, String> questions = new HashMap<String, String>();

    questions.put("1. Are you currently employed full time? (Y/N)", "Y");
    questions.put("2. Have you ever been declared bankrupt or had a foreclosure? (Y/N)", "N");
    questions.put("3. Have you missed any payments on your current debts in the past 12 months? (Y/N)", "N");

    return questions;
  }
}
