package org.vsg.common.model.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonSimpleDateTimeSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH");
	
	private SimpleDateFormat dateFormat_3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private SimpleDateFormat dateFormat_4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		String formattedDate = null;
		try {
			formattedDate = dateFormat_4.format(date);
		} catch (Exception e) {
			try {
				formattedDate = dateFormat_3.format(date);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				try {
					formattedDate = dateFormat_2.format(date);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					formattedDate = dateFormat_1.format(date);
				}
			}
		}
		
		
		if (formattedDate != null) {
			gen.writeString(formattedDate);
		}
	}

}
