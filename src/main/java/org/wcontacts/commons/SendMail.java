package org.wcontacts.commons;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

	public static void sendEmailUsingGmail(final String to, final String file,
			final String filename) {
		final String username = "wcontacttest@gmail.com";
		final String password = "W@12345678";
		final String from = "wcontacttest@gmail.com";
		final String subject = "Email for wContacts";
		final String msg = "PFA";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(msg);

			MimeBodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			DataSource source1 = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source1));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			System.out.println("Sending email...");

			Transport.send(message);

			System.out.println("Email Sent!!");

		} catch (Exception e) {
			
			System.out.println(e);
		}
	}

}
