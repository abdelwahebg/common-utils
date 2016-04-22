package com.github.becausetesting.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import com.github.becausetesting.properties.PropertyUtils;
import com.github.becausetesting.string.StringUtils;

public class EmailUtils {

	private static File emailfile;
	
	private MimeMultipart multipart;
	private BodyPart bodypart;

	static {
		PropertyUtils.setResourceBundle(emailfile);
	}

	public boolean sendEmail(String from, String to, String subject, String bodycontent) {

		String host = PropertyUtils.getBundleString("smtp.host");
		String port = PropertyUtils.getBundleString("smtp.port");
		String user = PropertyUtils.getBundleString("smtp.username");
		String password = PropertyUtils.getBundleString("smtp.password");

		// set these values into properties
		Properties prop = new Properties();
		// prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		// prop.put("mail.debug", "true");
		Session session = null;
		if (!StringUtils.isNullOrEmpty(user) || !StringUtils.isNullOrEmpty(password)) {
			prop.put("mail.smtp.auth", "true");
			// prop.put("mail.smtp.starttls.enable", "true");
			// prop.put("mail.smtp.ssl.enable","true");
			session = Session.getDefaultInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
		} else {
			session = Session.getDefaultInstance(prop);
		}

		try {
			MimeMessage mime = new MimeMessage(session);
			mime.setFrom(new InternetAddress(from));
			mime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mime.setSubject(subject);
			mime.setSentDate(new Date());

			// body content
			multipart = new MimeMultipart("related");
			bodypart = new MimeBodyPart();
			bodypart.setHeader("Content-Type", "text/html;charset=UTF-8");
			bodypart.setContent(bodycontent, "text/html;charset=UTF-8");
			multipart.addBodyPart(bodypart);
			System.out.println("complete parsing the html body content");

			// embed images

			// attachments file

			// send the email
			mime.setContent(multipart, "text/html;charset=UTF-8");
			Transport.send(mime);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private void addEmailImages(File image,String imageid){
		bodypart = new MimeBodyPart();
		DataSource ds = new FileDataSource(image);
		try {
			bodypart.setDataHandler(new DataHandler(ds));
			bodypart.addHeader("Content-ID", "<"+imageid+">");
			bodypart.setDisposition("inline");
			// bodypart.setFileName("logo.png");
			this.multipart.addBodyPart(bodypart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// be careful this content must contain with <>
		
	}
	private void addEmailAttachements(File attachment){
		bodypart = new MimeBodyPart();
		DataSource source = new FileDataSource(attachment);
		try {
			bodypart.setDataHandler(new DataHandler(source));
			bodypart.setFileName(attachment.getName());
			multipart.addBodyPart(bodypart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String receiveEmail(String filter) {

		return null;
	}


	public static void setEmailfile(File emailfile) {
		EmailUtils.emailfile = emailfile;
	}

}
