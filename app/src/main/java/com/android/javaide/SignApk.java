package com.android.javaide;

import com.android.lib.io.taGetOpt;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;

//##################################################################
/** This class uses Ken Ellinwoods zipsigner-lib to sign the APK file
 */
public class SignApk
//##################################################################
{
  // class variables
  private final String zipSignerVersion = "1.17";
  private taGetOpt getopt;
  private String mode = null;
  private String infilename = null;
  private String outfilename = null;
  private String keystore = null;
  private String storepass = null;
  private String alias = null;
  private String keypass = null;

//===================================================================
  public SignApk(String[] args) 
//===================================================================
  {
    getopt = new taGetOpt(args, "IO", "hMSPAW");
  } // constructor
//===================================================================
  private boolean fnCheckParams()
//===================================================================
  {
    boolean ok;
    int count;

    ok = getopt.paramsOK();
    if (getopt.getOption('h') != null) ok = false;
    fnReadParams();
    count = 0;
    if (keystore != null) count++;
    if (storepass != null) count++;
    if (alias != null) count++;
    if (keypass != null) count++;
    if ((count != 0) && (count != 4)) ok = false;
    if ((count == 0) && (mode == null)) ok = false;
    if (!ok)
    {
      System.out.println("SignApk is a wrapper for zipsigner-lib " + zipSignerVersion);
      System.out.println("\nUse: iExitValue = SignApk.main(arguments)");
      System.out.println("\nArguments can be:");
      System.out.println("-h help               show this help");
      System.out.println("-M keymode            auto, auto-testkey, auto-none, media, platform, shared, testkey, none");
      System.out.println("-I unsignedAPK        filename of the unsigned APK file");
      System.out.println("-O signedAPK          filename of the signed APK file");
      System.out.println("-S keystore           filename of the BKS keystore");
      System.out.println("-P storePass          keystore password");
      System.out.println("-A alias              certificate alias");
      System.out.println("-W keypass            private key password");
      System.out.println("\nValid combinations are:");
      System.out.println("-I, -O and -M");
      System.out.println("-I, -O, -S, -P, -A and -W. The -M option is ignored.");
      System.out.println("\nReturns following exit values:");
      System.out.println("0:   OK");
      System.out.println("1:   invalid or missing parameters");
      System.out.println("2:   signing error");
      System.out.println("255: undefined");
    } // if
    return ok;
  } // fnCheckParams
//===================================================================
  private void fnReadParams()
//===================================================================
  {
    mode = getopt.getOption('M');
    infilename = getopt.getOption('I');
    outfilename = getopt.getOption('O');
    keystore = getopt.getOption('S');
    storepass = getopt.getOption('P');
    alias = getopt.getOption('A');
    keypass = getopt.getOption('W');
  } //fnReadParams
//===================================================================
  private int fnSignApk()
//===================================================================
  {
    int rc = 255;
    ZipSigner signer;

    try
    {
      rc = 0;
      signer = new ZipSigner();
      if (keystore == null)
      {
        signer.setKeymode(mode);
        signer.signZip(infilename, outfilename);
      }// if buit-in key
      else
      {
        CustomKeySigner.signZip(
          signer, 
          keystore,
          storepass.toCharArray(),
          alias,
          keypass.toCharArray(),
          "SHA1withRSA", 
          infilename,
          outfilename
        );
      }// else custom key
    }// try
    catch (Throwable t)
    {
      rc = 2;
      if (t instanceof kellinwood.security.zipsigner.optional.LoadKeystoreException) System.out.println(t.toString());
      else if (t instanceof java.security.UnrecoverableKeyException) System.out.println(t.toString());
      else 
      {
        System.out.println(t.getMessage());
        t.printStackTrace();
      }
    }// catch all
    return rc;
  } // fnSignApk
//===================================================================
  public static int main2(String[] args)
//===================================================================
  {
    int rc = 255;
    boolean ok;

    SignApk main = new SignApk(args);
    ok = main.fnCheckParams();
    if (!ok) return 1;
    rc = main.fnSignApk();
    return rc;
  } // main2
//===================================================================
  public static void main(String[] args)
//===================================================================
  {
    int rc = main2(args);
    System.exit(rc);
  } // main
//===================================================================
} // SignApk
//##################################################################
