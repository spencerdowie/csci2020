package dataviz;

import java.io.*;
import java.util.*;

public class FileStorage {
   private static String filename = "data.csv";

   public static void saveData(Map<String,List<Integer>> housingPrices) {
      List<Integer> oneBedFlats = housingPrices.get("1 bed flats");
      List<Integer> twoBedFlats = housingPrices.get("2 bed flats");
      List<Integer> twoBedHouses = housingPrices.get("2 bed houses");
      List<Integer> threeBedHouses = housingPrices.get("3 bed houses");
      List<Integer> fourBedHouses = housingPrices.get("4 bed houses");

      try {
         PrintWriter out = new PrintWriter(filename);

         out.println("Year,1 bed flats,1 bed flats,2 bed houses,3 bed houses,4 bed houses");

         for (int i = 0; i < oneBedFlats.size(); i++) {
            out.print("" + (2002 + i) + ",");
            out.print(oneBedFlats.get(i) + ",");
            out.print(twoBedFlats.get(i) + ",");
            out.print(twoBedHouses.get(i) + ",");
            out.print(threeBedHouses.get(i) + ",");
            out.println(fourBedHouses.get(i));
         }

         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
