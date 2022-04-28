package co.edu.eam.Gestionpeliculasbackend.security.emailPassword.service;


import co.edu.eam.Gestionpeliculasbackend.security.emailPassword.dto.EmailValuesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${mail.urlFront}")
    private String urlFront;


    public void sendEmail(EmailValuesDTO dto){
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper= new MimeMessageHelper(mimeMessage,true);
            Context context= new Context();
            Map<String,Object> model= new HashMap<>();
            model.put("userName",dto.getUsername());
            model.put("url",urlFront+dto.getTokenPassword());
            context.setVariables(model);
            String htmlText= templateEngine.process("email-template",context);
            helper.setFrom(dto.getMailFrom());
            helper.setTo(dto.getMailTo());
            helper.setSubject(dto.getSubject());
            helper.setText(htmlText, true);
            javaMailSender.send(mimeMessage);

        }catch (MessagingException ex){
            ex.printStackTrace();
        }
    }

}
