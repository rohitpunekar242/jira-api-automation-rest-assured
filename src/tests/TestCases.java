package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;


public class TestCases {
	int issue_id;

	@BeforeMethod
	public void baseURI()
	{
		RestAssured.baseURI = "https://testalpha3242.atlassian.net";
	}

	@Test(priority=1)
	public void create_issue()
	{
		String CreateIssueResp = given().header("Content-Type","application/json").header("Authorization","Basic dGVzdGFscGhhOTU0NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSmhxRmcwVlZUeTFnMVAtNHplZVp2SGxTUVVfSlZldElwRlU5LVRReWNYREdYTWFGcmhaMEt5dGZzRHp1SEtFdXN0Rk8wOVhGREx3b2NHY3ptWDY4R2dWY1Q5emE1dkcxRHM3MTlKUFRTcWRJZEd6bE1iYWNYQjF4bkdqOHF6RjV3TFpjcWtQbDlidndmdVpwTXVrMEdyTkNCZGdlSnBfQ3d1QUpMQ09IVXVZPTU4NTJCNjJE")
				.body("{\r\n"
						+ "    \"fields\": {\r\n"
						+ "        \"project\": {\r\n"
						+ "            \"key\": \"SCRUM\"\r\n"
						+ "        },\r\n"
						+ "        \"summary\": \"Creating demo issue - 2\",\r\n"
						+ "        \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
						+ "        \"issuetype\": {\r\n"
						+ "            \"name\": \"Bug\"\r\n"
						+ "        }\r\n"
						+ "    }\r\n"
						+ "}").when().post("/rest/api/2/issue").then().assertThat().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(CreateIssueResp);
		issue_id = js.getInt("id");
		
		System.out.println("Issue created successfully");
	}

	@Test(priority=2)
	public void send_attachment()
	{
		given().header("Authorization","Basic dGVzdGFscGhhOTU0NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSmhxRmcwVlZUeTFnMVAtNHplZVp2SGxTUVVfSlZldElwRlU5LVRReWNYREdYTWFGcmhaMEt5dGZzRHp1SEtFdXN0Rk8wOVhGREx3b2NHY3ptWDY4R2dWY1Q5emE1dkcxRHM3MTlKUFRTcWRJZEd6bE1iYWNYQjF4bkdqOHF6RjV3TFpjcWtQbDlidndmdVpwTXVrMEdyTkNCZGdlSnBfQ3d1QUpMQ09IVXVZPTU4NTJCNjJE")
		.header("X-Atlassian-Token","no-check")
		.multiPart("file",new File("C:\\Users\\Admin\\OneDrive\\Pictures\\Screenshots\\AI.jpg"))
		.pathParam("key", issue_id)
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().assertThat().statusCode(200);
		
		System.out.println("Attchment sent successfully");
	}

	@Test(priority=3)
	public void update_issue()
	{
		given().header("Content-Type","application/json").header("Authorization","Basic dGVzdGFscGhhOTU0NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSmhxRmcwVlZUeTFnMVAtNHplZVp2SGxTUVVfSlZldElwRlU5LVRReWNYREdYTWFGcmhaMEt5dGZzRHp1SEtFdXN0Rk8wOVhGREx3b2NHY3ptWDY4R2dWY1Q5emE1dkcxRHM3MTlKUFRTcWRJZEd6bE1iYWNYQjF4bkdqOHF6RjV3TFpjcWtQbDlidndmdVpwTXVrMEdyTkNCZGdlSnBfQ3d1QUpMQ09IVXVZPTU4NTJCNjJE")
		.pathParam("key", issue_id)
		.body("{\r\n"
				+ "   \"fields\": {\r\n"
				+ "       \"summary\":\"New summary\",\r\n"
				+ "       \"description\":\"New description\"\r\n"
				+ "   }\r\n"
				+ "}")
		.when().put("rest/api/2/issue/{key}")
		.then().assertThat().statusCode(204);
		
		System.out.println("Issue updated successfully");
	}
	
	@Test(priority=4)
	public void delete_issue()
	{
		given().header("Content-Type","application/json").header("Authorization","Basic dGVzdGFscGhhOTU0NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSmhxRmcwVlZUeTFnMVAtNHplZVp2SGxTUVVfSlZldElwRlU5LVRReWNYREdYTWFGcmhaMEt5dGZzRHp1SEtFdXN0Rk8wOVhGREx3b2NHY3ptWDY4R2dWY1Q5emE1dkcxRHM3MTlKUFRTcWRJZEd6bE1iYWNYQjF4bkdqOHF6RjV3TFpjcWtQbDlidndmdVpwTXVrMEdyTkNCZGdlSnBfQ3d1QUpMQ09IVXVZPTU4NTJCNjJE")
		.pathParam("key", issue_id)
		.when().delete("rest/api/3/issue/{key}")
		.then().assertThat().statusCode(204);
		
		System.out.println("Issue deleted successfully");
	}
	
	@Test(priority=5)
	public void delete_validation()
	{
		given().header("Content-Type","application/json").header("Authorization","Basic dGVzdGFscGhhOTU0NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSmhxRmcwVlZUeTFnMVAtNHplZVp2SGxTUVVfSlZldElwRlU5LVRReWNYREdYTWFGcmhaMEt5dGZzRHp1SEtFdXN0Rk8wOVhGREx3b2NHY3ptWDY4R2dWY1Q5emE1dkcxRHM3MTlKUFRTcWRJZEd6bE1iYWNYQjF4bkdqOHF6RjV3TFpjcWtQbDlidndmdVpwTXVrMEdyTkNCZGdlSnBfQ3d1QUpMQ09IVXVZPTU4NTJCNjJE")
		.pathParam("key", issue_id)
		.when().get("rest/api/3/issue/{key}")
		.then().assertThat().statusCode(404);
		
		System.out.println("Issue does not exist anymore");
	}
	

}
