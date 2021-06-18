package stepdefs;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class GenericStepDefinitions {
	
	private StepData stepData;

	public GenericStepDefinitions(StepData stepData) {
		this.stepData = stepData;
	}
	
	@Then("The status code is (\\d+)")
	public void verify_status_code(int statusCode){
		stepData.json = stepData.response.then().statusCode(statusCode);
	}

	@Then("I print all the logs on Console")
	public void print_allLogs()
	{
		stepData.response.then().log().all();
	}
	
	@And("Response includes the following$")  //Verifying single content using equalTo method of hamcrest library
	public void response_equals(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				stepData.json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			}
			else{
				stepData.json.body(field.getKey(), equalTo(field.getValue()));
			}
		}
	}

	@And("Response includes the following in any order")
	public void response_contains_in_any_order(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				stepData.json.body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
			}
			else{
				stepData.json.body(field.getKey(), containsInAnyOrder(field.getValue()));
				//json.body(field.getKey(), hasItems(field.getValue()));   //This can also be used
			}
		}
	}
	



}
