package com.hometask.task_security.consoleInfo;

import com.hometask.task_security.model.Email;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InfoFromUser {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public Email createNewEmail() throws IOException {

        String recipient = recipientValidation();
        String subject = emailSubject();
        String body = bodyValidation();
        LocalDateTime date = dateValidation();
        return new Email(recipient, subject, body, date);

    }

    public int numberOfEmailsValidation() throws IOException {
        System.out.println("This is an email deliver.\nPlease, enter a number of emails you want to send and press Enter");
        int numberOfEmails = 0;
        int count = 0;
        do {
            try {
                count = 0;
                numberOfEmails = Integer.parseInt(READER.readLine());
            } catch (NumberFormatException e) {
                count++;
                System.err.println("You should use only digits. Try again.");
            }
        } while (count != 0);
        return numberOfEmails;
    }

    public String recipientValidation() throws IOException {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        System.out.println("Please, enter email address of recipient \nFor example: \"YourEmail@gmail.com\"");
        while (true) {
            String recipient = READER.readLine();
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(recipient);
            if (m.find()) {
                return recipient;
            }
            System.err.println("You entered email incorrectly, try again");
        }
    }

    public String emailSubject() throws IOException {
        System.out.println("Please,enter the subject of your email");
        return READER.readLine();
    }

    public String bodyValidation() throws IOException {
        StringBuilder builder = new StringBuilder();
        System.out.println("Please, type your message, press the ENTER once and type the word \"End\"");
        do {
            String body = READER.readLine();
            if (body.equalsIgnoreCase("end")) {
                return builder.toString();
            }
            builder.append(body).append(". ");
        } while (true);
    }

    public LocalDateTime dateValidation() throws IOException {
        LocalDateTime time = null;
        System.out.println(" Date of sending should be entered only in this format: \n day month year hours:minutes \n for example - 20 11 2020 19:15\n Please, enter the date of sending :");
        int count = 0;
        do {
            try {
                count = 0;
                time = LocalDateTime.parse(READER.readLine(), FORMATTER);
            } catch (DateTimeParseException exception) {
                count++;
                System.err.println("Enter the date according to the format and try again");
            }
        } while (count != 0);
        return time;
    }

}
