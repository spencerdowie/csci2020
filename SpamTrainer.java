package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.*;
import java.util.*;

public class SpamTrainer {
    private Map<String, Integer> spamCounts, hamCounts;
    private Double spamFiles = 0.0, hamFiles = 0.0, numTruePositives = 0.0, numTrueNegatives = 0.0, numFalsePositives = 0.0;
    public Double accuracy = 0.0, precision = 0.0;

    public SpamTrainer()
    {
        spamCounts = new TreeMap<>();
        hamCounts = new TreeMap<>();
    }

    private boolean isWord(String word)
    {
        String pattern = "^[a-z]+$";
        boolean b = word.matches(pattern);
        return b;
    }

    private void countSpam(String word)
    {
        if(spamCounts.containsKey(word))
        {
            int oldCount = spamCounts.get(word);
            spamCounts.put(word, oldCount+1);
        }
        else
        {
            spamCounts.put(word, 1);
        }
    }

    private void countHam(String word)
    {
        if(hamCounts.containsKey(word))
        {
            int oldCount = hamCounts.get(word);
            hamCounts.put(word, oldCount+1);
        }
        else
        {
            hamCounts.put(word, 1);
        }
    }

    public ObservableList<TestFile> processTestFolder(File file) throws IOException
    {

        ObservableList<TestFile> files = FXCollections.observableArrayList();
        File[] folders = file.listFiles();

        for (File folder: folders) {
            if (folder.isDirectory()) {
                String fileName = folder.getName();
                String actualClass;
                if (fileName.contains("ham")) {
                    actualClass = "ham";
                } else {
                    actualClass = "spam";
                }
                File[] contents = folder.listFiles();
                for (File current : contents) {
                    TestFile test = new TestFile(current.getName(), testFile(current), actualClass);
                    files.add(test);
                    if(test.getSpamProbability() > 0.5) {
                        if (actualClass.matches("ham")) {
                            numFalsePositives++;
                        }
                        else
                        {
                            numTruePositives++;
                        }
                    }
                    else
                    {
                        if (actualClass.matches("ham")) {
                            numTrueNegatives++;
                        }
                    }
                }
            }
        }
        accuracy = (numTruePositives + numTrueNegatives) / files.size();
        precision = numTruePositives / (numFalsePositives + numTruePositives);
        System.out.println("Acc: " + accuracy + " Prec: " + precision);
        return files;
    }

    public void processTrainFolder(File file) throws IOException
    {
        File[] folders = file.listFiles();

        for (File folder: folders) {
            if (folder.isDirectory()) {
                String fileName = folder.getName();
                if (fileName.contains("ham")) {
                    File[] contents = folder.listFiles();
                    for (File current : contents) {
                        hamFiles++;
                        processHam(current);
                    }
                } else if (fileName.contains("spam")) {
                    File[] contents = folder.listFiles();
                    for (File current : contents) {
                        spamFiles++;
                        processSpam(current);
                    }
                }
            }
        }
    }

    public void processHam(File file) throws IOException
    {
        if(file.exists())
        {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[\\s\\.;:\\?\\!,]");
            while(scanner.hasNext())
            {
                String word = scanner.next();
                word = word.toLowerCase();
                if(isWord(word))
                {
                    countHam(word);
                }
            }
        }
    }

    public void processSpam(File file) throws IOException
    {
        if(file.exists())
        {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[\\s\\.;:\\?\\!,]");
            while(scanner.hasNext())
            {
                String word = scanner.next();
                word = word.toLowerCase();
                if(isWord(word))
                {
                    countSpam(word);
                }
            }
        }
    }

    public Double testFile(File file) throws IOException
    {
        Double n = 0.0;

        if(file.exists())
        {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[\\s\\.;:\\?\\!,]");
            while(scanner.hasNext())
            {
                String word = scanner.next();
                word = word.toLowerCase();
                if(isWord(word)) {
                    if (spamCounts.containsKey(word) && hamCounts.containsKey(word)) {
                        Double PSW, PWS, PWH;

                        PWS = spamCounts.get(word) / spamFiles;
                        PWH = hamCounts.get(word) / hamFiles;
                        if(PWS == 1 && PWS == 0)
                            System.out.println("BAD");
                        PSW = PWS / (PWS + PWH);

                        n += Math.log(1 - PSW) - Math.log(PSW);
                    }
                }
            }
        }

        Double PSF = 1/(1+Math.pow(Math.E, n));
        //System.out.println("N value " + n);
        return PSF;
    }
}
