package com.github.phalexei.sig;

import com.github.phalexei.sig.questions.Question;

public class Main {

    /**
     * @param args Arguments for
     *             <li> Question9 : 9 dom__ne uni_er_it%
     *             <li> Question10_A : 10 A
     *             <li> Question10_B : 10 B
     *             <li> Question10_C : 10 C
     *             <li> Question11_A : 11 A
     *             <li> Question11_B : 11 B
     */
    public static void main(String[] args) {
        try {
            parseArguments(args).answer();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the question corresponding to arguments
     *
     * @param args : array args (from main)
     * @return
     */
    public static Question parseArguments(String[] args) throws IllegalArgumentException {

        int questionNumber = Integer.parseInt(args[0]);
        Character questionLetter = null;
        StringBuilder s = new StringBuilder();

        if (questionNumber == 10 | questionNumber == 11 && args[1].length() == 1) {
            questionLetter = args[1].charAt(0);
        } else {
            for (int i = 1; i < args.length; i++) {
                System.out.println(args[i]);
                s.append(args[i]).append(" ");
            }

            if (s.length() != 0) {
                s.deleteCharAt(s.length() - 1);
            }
        }

        return Question.newQuestion(questionNumber, questionLetter, s.toString());
    }
}
