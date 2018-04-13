package dataviz;

import org.json.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HousingPricesLoader
{
    public Map<String, List<Integer>> loadPrices()
    {
        Map<String, List<Integer>> map = new TreeMap<>();
        try
        {
            //From url
//            URL netUrl = new URL("http://csundergrad.science.uoit.ca/csci2020u/data/housing_prices.json");
//            URLConnection conn = netUrl.openConnection();
//            conn.setDoOutput(false);
//            conn.setDoInput(true);
//            BufferedReader inURL = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            //From file
            File file = new File("housing_prices.json");
            BufferedReader in = new BufferedReader(new FileReader(file));

            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = in.readLine())!=null)
            {
                buffer.append(line);
            }
            String jsonString = buffer.toString();

            JSONArray data = new JSONArray(jsonString);
            for(int i = 0; i < data.length(); i++)
            {
                JSONObject year = data.getJSONObject(i);
                String yearStr = String.valueOf(year.getInt("Year"));
                List<Integer> prices = new ArrayList<>();
                prices.add(year.getInt("1 bed flats"));
                prices.add(year.getInt("2 bed flats"));
                prices.add(year.getInt("2 bed houses"));
                prices.add(year.getInt("3 bed houses"));
                prices.add(year.getInt("4 bed houses"));
                map.put(yearStr, prices);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return map;
    }
}
