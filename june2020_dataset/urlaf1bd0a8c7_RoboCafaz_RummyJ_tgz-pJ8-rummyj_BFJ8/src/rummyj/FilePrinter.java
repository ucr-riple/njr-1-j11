package rummyj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter
{
  static FileWriter     fileStream;
  static BufferedWriter outBuffer;
  static int            tabCount = 0;

  public static void increaseIndent()
  {
    tabCount++;
  }

  public static void decreaseIndent()
  {
    tabCount--;
    if (tabCount < 0)
    {
      tabCount = 0;
    }
  }

  public static void openBuffer(String fileName, String fileExtension)
  {
    try
    {
      fileStream = new FileWriter(fileName + "." + fileExtension);
      outBuffer = new BufferedWriter(fileStream);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void write(String line)
  {
    try
    {
      for (int i = 0; i < tabCount; i++)
      {
        outBuffer.write("\t");
      }
      outBuffer.write(line);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void write(char character)
  {
    write("" + character);
  }

  public static void writeLine()
  {
    writeLine("");
  }

  public static void writeLine(char character)
  {
    writeLine("" + character);
  }

  public static void writeLine(String line)
  {
    write(line + "\n");
  }

  public static void closeBuffer()
  {
    try
    {
      outBuffer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
