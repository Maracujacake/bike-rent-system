package service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
public class EmailService {

    public static void sendEmail(String email, String subject, String message) throws IOException {
        Email from = new Email("thativancapeli@gmail.com");
        Email to = new Email(email);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
        Dotenv dotenv = Dotenv.load();
        SendGrid sg = new SendGrid(dotenv.get("EMAILKEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            // System.out.println(response.getStatusCode());
            // System.out.println(response.getBody());
            // System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
  }
    
}
