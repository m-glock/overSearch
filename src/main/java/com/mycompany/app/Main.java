package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File folder = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Netzwerke");
        File[] listOfFiles = folder.listFiles();

        HashMap<String, String> namesAndDates = getNamesAndDates(listOfFiles);

        try (PrintWriter out = new PrintWriter("filename.txt"))  {
            for(HashMap.Entry<String,String> mapValue : namesAndDates.entrySet()){
                out.println(mapValue.getKey() + ": " + mapValue.getValue());
            }

        }
    }

    public static HashMap<String, String> getNamesAndDates(File[] listOfFiles){
        HashMap<String, String> fileList = new HashMap<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String modifiedDate = sdf.format(listOfFiles[i].lastModified());

                fileList.put(fileName, modifiedDate);
            } else if (listOfFiles[i].isDirectory()) {
                String fileName = "Directory: " + listOfFiles[i].getName();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String modifiedDate = sdf.format(listOfFiles[i].lastModified());

                fileList.put(fileName, modifiedDate);
            }
        }
        return fileList;
    }
}