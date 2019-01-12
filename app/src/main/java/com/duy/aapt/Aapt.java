package com.duy.aapt;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

//##################################################################
public class Aapt
//##################################################################
{
    private static final File dirLog = new File("/storage/emulated/0/.AaptJNI");

    private static final File txtOut = new File(dirLog, "native_stdout.txt");
    private static final File txtErr = new File(dirLog, "native_stderr.txt");

    private static boolean bInitialized = false;
	private native int JNImain(String args);
//===================================================================
    public Aapt()
//===================================================================
	{
        if (!bInitialized)
		{
            fnInit();
        }
    }// constructor
//===================================================================
    private static boolean fnInit()
//===================================================================
	{
        try
		{
            dirLog.mkdirs();
            System.out.println("Loading native library aapt...");
            System.loadLibrary("aapt");
            bInitialized = true;
            System.out.println("Aapt has been loaded.");
            return true;
        }
		catch (Exception e)
		{
            System.err.println(e.getMessage());
            return false;
        }
    }// fnInit
//===================================================================
    public boolean isInitialized()
//===================================================================
	{
        return bInitialized;
    }//
//===================================================================
    public synchronized int fnExecute(String args)
//===================================================================
	{
        int rc = 99;
        System.out.println("Calling JNImain...");
        rc = JNImain(args.replace(' ', '\t'));
        System.out.println("Result from native lib=" + rc);
        fnGetNativeOutput();
        return rc;
    }// fnExecute
//===================================================================
    private void fnGetNativeOutput()
//===================================================================
	{
        LineNumberReader lnr;
        String st = "";
        try
		{
            lnr = new LineNumberReader(new FileReader(txtOut));
            st = "";
            while (st != null)
			{
                st = lnr.readLine();
                if (st != null)
                    System.out.println(st);
            }// while
            lnr.close();
            lnr = new LineNumberReader(new FileReader(txtErr));
            st = "";
            while (st != null)
			{
                st = lnr.readLine();
                if (st != null)
                    System.err.println(st);
            }// while
            lnr.close();
        }
		catch (Exception e)
		{
            System.err.println(e.getMessage());
        }
    }//
//===================================================================
}
//##################################################################
