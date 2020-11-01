package tn.esprit.spring.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

@RunWith(SpringRunner.class)
public class RestControlEntrepriseTest {
	@Autowired
	private  RestControlEntreprise ec;
	
	 @Test
	 public void getAllEmployeByMissionTest() throws JsonProcessingException {
		 CloseableHttpClient client = HttpClients.createDefault();
		    HttpPost httpPost = new HttpPost("http://www.example.com");
		 
		    String json = "{"id":1,"name":"John"}";
		    StringEntity entity = new StringEntity(json);
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		 
		    CloseableHttpResponse response = client.execute(httpPost);
		    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		    client.close();
	}

}
