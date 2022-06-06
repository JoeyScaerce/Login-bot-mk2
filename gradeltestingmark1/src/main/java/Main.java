/**
Name: Kenja Palmer
Date: 4/18-5/22
 Name: Sam Lawson
 Date: 5/22-
*/

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String useremail;
        String hostName;


        // create the config file & check if the file exist, so it can write down in it.
        Configurator.createConfig();
        //System.out.println(Configurator.getSender_Email());

        // validation process. if it fails a validation check then it should prompt the user to put in there email again.
        do {

            // gets the email
            useremail = askForEmail();

            // gets the emails domain
            hostName = EmailValidation.getDomain(useremail);

            // in the while loop it has the 2 email checkers.
        }while (!EmailValidation.isValidEmail(useremail) || !EmailValidation.doesEmailDomainExist(hostName));

        // after all checks this will send the email out to the user.
        sendingEmail(useremail);

        //apples(useremail);
    }

    /*public static String apples(String useremail) {

        String conten_Of_Msg;

        if (passwordChange()) {

        }


        return ;
    }*/

    public static boolean passwordChange() {
        System.out.print("Would you like to change your password? >>> ");
        Scanner s = new Scanner(System.in);
        boolean change_password = s.nextBoolean();

        if (change_password) {
           return true;
        } else {
            return false;
        }
    }



    // gets the user email from a prompt
    public static String askForEmail() {
        Scanner input = new Scanner(System.in);
        System.out.print("what is your email >> ");
        String email = input.nextLine();
        System.out.println(email);
        return email;
    }

    // method for sending out the emailed
    public static void sendingEmail(String useremail) {

        // variable for the Bot_Email config.
        final String user = Configurator.getSender_Email();
        // variable for the password config.
        final  String password = Configurator.getPassword();


        // protocols for setting the email.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Configurator.getSmtp_Host());
        props.put("mail.smtp.ssl.trust", Configurator.getSmtp_Trust());
        props.put("mail.smtp.port", Configurator.getSmtp_port());
        props.put("mail.smtp.ssl.protocols", Configurator.getSmtp_ssl_protocols());

        // starts a session for the email. allowing the code to connect to it.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            // set the msg from the email we're using from the user variable
            msg.setFrom(new InternetAddress(user));
            // send the msg to what the computer got as the recipient email.
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(useremail));
            // the subject of the email
            msg.setSubject("Getting started with Spyglass");
            // responds of the email
            msg.setContent("<h3 style=\"text-align:center;\">Welcome to <b>SpyGlass</b></h3>"+
                    "<p style=\"text-align:center;\">Please use the following link to <a href=\"https://youtu.be/YzDsdB4laI0\">verify your account</a></p>"+
                    "<br><br><p style=\"text-align:center;\"><font size=1>If you did not recently create an account with us, please ignore this email</font></p>"
                    + "<p style=\"text-align:center;\"><img src=\"https://i1.sndcdn.com/artworks-000145025252-tvteyq-t500x500.jpg\" alt=\"Logo\" width=\"300\" height=\"300\"></p>", "text/html");
            // sends the msg final step
            Transport.send(msg);
            System.out.println("A msg has been sent to this email address " + useremail);
        }catch (Exception  e) {
            // if the information from the session can not connect to a session. send this msg.
            System.out.println("an error has occurred");
        }
    }

}
