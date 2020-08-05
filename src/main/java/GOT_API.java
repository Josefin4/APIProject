import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
import java.net.*;

public class GOT_API {
    public JSONObject getObject(String str){
        StringBuffer content = getJSONObject(str);
        JSONObject json = new JSONObject(content.toString());
        return json;
    }

    public JSONArray getArray(String str){
        StringBuffer content = getJSONArray(str);
        JSONArray json = new JSONArray(content.toString());
        return json;
    }

    public StringBuffer getJSONObject(String str) {
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String inputLine;
                StringBuffer content = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    content.append(inputLine);
                }
                br.close();

                return content;

            } else {
                System.out.println("Error");
                System.out.println("Server responded with: " + con.getResponseCode());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return null;
    }


    public StringBuffer getJSONArray(String str) {
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String inputLine;
                StringBuffer content = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    content.append(inputLine);
                }
                br.close();

                return content;

            } else {
                System.out.println("Error");
                System.out.println("Server responded with: " + con.getResponseCode());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return null;
    }

}


