package services;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.apache.http.HttpStatus;

public class BookTests
{
	private static String ENDPOINT_GET_BOOK_BY_ID = "https://reqres.in/api/users/2";

	//@Test  -- Uncomment this to execute without feature file/ Execution With TestNG
	public void testGetByID(){
		String id = "id:2";

		given().
		param("q", id).
		when().
		get(ENDPOINT_GET_BOOK_BY_ID)
		.then().
		statusCode(HttpStatus.SC_OK).
		body(	"totalItems", equalTo(1),
				"kind", equalTo("books#volumes"),
				"items.volumeInfo.title", containsInAnyOrder("Steve Jobs"),
				"items.volumeInfo.authors", containsInAnyOrder((Object)Arrays.asList("Walter Isaacson")),
				"items.volumeInfo.publisher", containsInAnyOrder("Simon and Schuster"),
				"items.volumeInfo.pageCount", containsInAnyOrder(630));
	}
}
