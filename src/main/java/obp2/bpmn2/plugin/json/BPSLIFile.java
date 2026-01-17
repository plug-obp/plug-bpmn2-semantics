package obp2.bpmn2.plugin.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BPSLIFile {

    @JsonProperty("modelFileName")
    public String modelFileName;

    @JsonProperty("flowCompletion")
    public boolean flowCompletion = false;

    @JsonProperty("rootProcesses")
    public String[] rootProcesses = {};

    @JsonProperty("includeCalledProcesses")
    public boolean includeCalledProcesses = true;

    public static String EXTENSION = ".bpsli";

    public static BPSLIFile loadFromFile(URI uri) {
        try {
            StringBuilder fileContent = new StringBuilder();
            Files.lines(Paths.get(uri)).forEach(fileContent::append);
            String jsonString = fileContent.toString();
            return new ObjectMapper().readValue(jsonString, BPSLIFile.class);
        } catch (Exception e) {
            return null;
        }
    }

}
