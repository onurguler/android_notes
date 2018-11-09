package com.onurguler.notes;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller
{
    public static boolean edit = false;

    public static void generateNote(Context context, String sFileName, String sBody)
    {
        try
        {
            File root = new File(context.getFilesDir(), "Notes");
            if (!root.exists())
                root.mkdirs();
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String readNote(Context context, String sFileName)
    {

        File sdcard = new File(context.getFilesDir(), "/Notes");
        File file = new File(sdcard, sFileName);
        StringBuilder text = new StringBuilder();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null)
            {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return text.toString();
    }

    public static ArrayList<String> getFiles(Context context)
    {
        ArrayList<String> arrayListFiles = new ArrayList<>();

        File root = new File(context.getFilesDir(), "Notes");

        if (!root.exists())
            root.mkdirs();

        File[] files = root.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            String file = files[i].getName();
            arrayListFiles.add(file.substring(0, file.length()-4)); // substring .txt
        }

        return arrayListFiles;
    }

    public static void deleteFiles(Context context, String fileName)
    {
        String path = context.getFilesDir().toString() + "/Notes/" + fileName + ".txt";
        File file = new File(path);

        if (file.exists())
        {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try
            {
                runtime.exec(deleteCmd);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
