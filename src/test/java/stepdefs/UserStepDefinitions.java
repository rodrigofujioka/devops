package stepdefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class UserStepDefinitions {
	
	private StepData stepData;

	public UserStepDefinitions(StepData stepData) {
		this.stepData = stepData;
	}

	@Given("I set Headers and Parameters for request")
	public void set_hearders_parameters(DataTable dt)
	{
		List<Map<String, String>> list = dt.asMaps(String.class, String.class);
		for(int i=0; i<list.size(); i++) {
			if(StringUtils.isNumeric(list.get(i).get("VALUE"))){
				stepData.request=given().param(list.get(i).get("KEY"), Integer.parseInt(list.get(i).get("VALUE")));
			}
			else{
				given().param(list.get(i).get("KEY"), list.get(i).get("VALUE")); 
			}
		}
	}
	
	@Given("User hit the webservice (.*)")
	public void user_hit_the_webservice(String WebServiceURL){
		stepData.response = stepData.request.when().get(WebServiceURL);
	}
	
	@Then("I convert response in String Format")
	public String get_response_string(String WebServiceURL)
	{
		String	ResponseString = stepData.response.toString();
		return ResponseString;
	}
	
	@And("I extract url from response body and hit that url to verify if that is working")
	public void extract_url_from_esponse_body_hit_that_and_verify_response()
	{
		String href=
				stepData.response.then().extract().path("url");
				stepData.request.when().get(href).then().statusCode(200);
	}
	
	@And("I get cookies")
	public void get_cookies()
	{
		Map<String,String> cookies = stepData.response.getCookies();
		for(Map.Entry<String,String> entry:cookies.entrySet())
		{
			System.out.println(entry.getKey()+": "+entry.getValue());	
		}
	}
		
	@And("I send POST request with below parameters")
	public void send_post_request(DataTable dt)
	{
		List<Map<String, String>> list = dt.asMaps(String.class, String.class);
		for(int i=0; i<list.size(); i++) {
			if(StringUtils.isNumeric(list.get(i).get("VALUE"))){
				stepData.request=given().multiPart(list.get(i).get("KEY"), Integer.parseInt(list.get(i).get("VALUE")));
			
			}
			else{
				stepData.request=given().multiPart(list.get(i).get("KEY"), list.get(i).get("VALUE")); 
			}
		}
	}
	
	@Given("I hit the webservice (.*) with post request")
	public void user_sends_post_request(String WebServiceURL){
		stepData.response = stepData.request.when().post(WebServiceURL);
	}
	
	
}
		