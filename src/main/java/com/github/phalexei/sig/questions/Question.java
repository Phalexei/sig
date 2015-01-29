package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.database.Utils;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.sql.Connection;

public abstract class Question {

    private Connection connection;

    public Question() {
    }

    public final void answer() {
        this.connection = Utils.getConnection();

        this.answerInternal();

        Utils.closeConnection();
    }

    protected final Connection getConnection() {
        return this.connection;
    }

    public abstract void answerInternal();

    public static Question newQuestion(int questionNumber, Character questionLetter, String arg) throws InvalidArgumentException {
        switch (questionNumber) {
            case 9:
                return new Question9(arg);
            case 10:
                switch (Character.toLowerCase(questionLetter)) {
                    case 'a':
                        return new Question10a();
                    case 'b':
                        return new Question10b();
                    case 'c':
                        return new Question10c();
                    default:
                        throw new InvalidArgumentException(new String[]{"Question 10 only has variants A, B or C." + questionLetter + " is invalid"});
                }
            case 11:
                switch (Character.toLowerCase(questionLetter)) {
                    case 'a':
                        return new Question11a();
                    case 'b':
                        return new Question11b();
                    case 'c':
                        return new Question11c();
                    default:
                        throw new InvalidArgumentException(new String[]{"Question 11 only has variants A, B or C." + questionLetter + " is invalid"});
                }
            default:
                throw new InvalidArgumentException(new String[]{"You must choose from question 9, 10 or 11"});
        }
    }
}
