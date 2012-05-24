/**
 * 
 */
package com.zenika.cnamts.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.zenika.cnamts.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public class CsvHttpMessageConverter implements HttpMessageConverter<Contact> {
	
	public static final MediaType CSV_MEDIA_TYPE = new MediaType("text","csv");

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return Contact.class.equals(clazz) && (CSV_MEDIA_TYPE.includes(mediaType) || mediaType == null);
	}
	
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return Contact.class.equals(clazz) && (CSV_MEDIA_TYPE.includes(mediaType) || mediaType == null);
	}
	
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Collections.singletonList(CSV_MEDIA_TYPE);
	}
	
	@Override
	public Contact read(Class<? extends Contact> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		String line = new String(IOUtils.toString(inputMessage.getBody()));
		String [] split = StringUtils.split(line,",");
		Contact contact = new Contact();
		contact.setId(Long.valueOf(split[0]));
		contact.setFirstname(split[1]);
		contact.setLastname(split[2]);
		contact.setAge(Integer.valueOf(split[3]));
		return contact;
	}
	
	@Override
	public void write(Contact t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		outputMessage.getHeaders().setContentType(CSV_MEDIA_TYPE);
		String line = t.getId()+","+t.getFirstname()+","+t.getLastname()+","+t.getAge();
		outputMessage.getBody().write(line.getBytes());
		outputMessage.getBody().flush();
	}
}
