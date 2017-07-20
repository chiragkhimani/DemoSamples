package wallethub.automation.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import cucumber.api.Scenario;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.minidev.json.JSONArray;

public class ReportUtil {
	private static ThreadLocal<Scenario> scenario = new ThreadLocal<Scenario>();

	// After execution of test this method will be called to genereate customize
	// report
	public static void main(String[] s) {
		generateReport();
	}

	private static void generateReport() {
		try {
			createReport();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File reportOutputDirectory = new File("report");

		String buildNumber = "1";
		String projectName = "cucumberProject";
		boolean runWithJenkins = false;
		boolean parallelTesting = false;

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		// optional configuration
		configuration.setParallelTesting(parallelTesting);
		configuration.setRunWithJenkins(runWithJenkins);
		configuration.setBuildNumber(buildNumber);

		ReportBuilder reportBuilder = new ReportBuilder(getJsonFiles(), configuration);
		reportBuilder.generateReports();
	}

	// Customize cucumber.json as per client requirement
	private static void createReport() throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get("target//cucumber.json")));
		String updatedJson = contents;
		String jsonPathStatus = "$.[%s].elements[%s].steps[%s].result.status";
		String jsonPathOutputList = "$.[%s].elements[%s].steps[%s].output";
		String jsonPathFeatureFileCount = "$.*";
		String jsonPathScenarioCount = "$.[%s].elements";
		String jsonPathStepsCount = "$.[%s].elements[%s].steps";
		String jsonPathBeforeHooks = "$.[%s].elements[%s].before";
		String jsonPathAfterHooks = "$.[%s].elements[%s].after";

		JSONArray jArrayFeature = (JSONArray) JsonPath.read(contents, jsonPathFeatureFileCount);
		JSONArray jArrayOutput = new JSONArray();
		for (int i = 0; i < jArrayFeature.size(); i++) {
			JSONArray jArrayScenario = (JSONArray) JsonPath.read(contents, String.format(jsonPathScenarioCount, i));
			for (int j = 0; j < jArrayScenario.size(); j++) {
				JSONArray jArraySteps = (JSONArray) JsonPath.read(contents, String.format(jsonPathStepsCount, i, j));
				updatedJson = deleteHooks(String.format(jsonPathBeforeHooks, i, j), updatedJson);
				updatedJson = deleteHooks(String.format(jsonPathAfterHooks, i, j), updatedJson);
				for (int k = 0; k < jArraySteps.size(); k++) {
					try {
						jArrayOutput = (JSONArray) JsonPath.read(contents, String.format(jsonPathOutputList, i, j, k));
					} catch (Exception e) {

					}
					for (int l = 0; l < jArrayOutput.size(); l++) {
						if (jArrayOutput.get(l).toString().contains("cross.png")) {

							updatedJson = changeStatusToFailed(updatedJson, String.format(jsonPathStatus, i, j, k, l));
							contents = updatedJson;

						}
					}
					jArrayOutput = new JSONArray();
				}
			}
		}
		writeToFile("target//cucumber.json", updatedJson);
	}

	public static String changeStatusToFailed(String json, String jsonPath) {
		Object updatedJson = JsonPath.parse(json).set(jsonPath, "failed").json();
		return updatedJson.toString();
	}

	// Delete unwanted things from the report
	public static String deleteHooks(String jsonPath, String contents) {
		Object updatedJson = JsonPath.parse(contents).delete(jsonPath).json();
		return updatedJson.toString();
	}

	public static boolean writeToFile(String filePath, String content) {
		File myFoo = new File(filePath);
		FileWriter fooWriter;
		try {
			fooWriter = new FileWriter(myFoo, false);
			fooWriter.write(content + "\n");
			fooWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public static List<String> getJsonFiles() {
		List<String> jsonFiles = new ArrayList<String>();
		try {
			File dir = new File("target//");
			if (!dir.isDirectory())
				throw new IllegalStateException("");
			for (File file : dir.listFiles()) {
				if (file.getName().startsWith("cucumber") && !file.isDirectory()) {
					System.out.println(file.getAbsolutePath());
					jsonFiles.add(file.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonFiles;
	}

	public static void setScenario(Scenario s) {
		scenario.set(s);
	}

	public static Scenario getScenario() {
		return scenario.get();
	}

}
