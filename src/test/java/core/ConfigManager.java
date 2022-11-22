package core;

import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.FileReader;

public class ConfigManager {

    /**
     * Reads the file content and coverts to string
     *
     * @param filePath String path to the target file
     * @return String: file content converted to string
     */
    private String read(String filePath) {
        String finalText = null;
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            finalText = sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalText;
    }


    /**
     * Giving the file that contains JSON file, it converts the JSON format
     * to the String data, and returns to the caller.
     *
     * @return String: json data converted to string
     */
    private String getConfig() {
        String path = System.getProperty("user.dir") + "/testConfig.json";
        String payload = read(path).trim();
        return payload;
    }


    /**
     * Given JSON data, it will extract the target value
     * specified by Json Path query.
     *
     * @param query json path query
     * @param <T>   extracted data's data type
     * @return extracted data
     */
    public <T> T extract(String query) {
        T extracted = JsonPath.read(getConfig(), query);
        return extracted;
    }

}//end::class
