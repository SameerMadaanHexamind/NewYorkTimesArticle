package com.scorematics.android.utils;/*
package com.saavi.android.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

*/
/**
 * Created by sameer.madaan on 5/8/2017.
 *//*


public class FTPUtils {
    private static FTPClient mFtpClient;

    */
/**
     *
     * @param ip
     * @param userName
     * @param pass
     *//*

    public static FTPClient connnectingwithFTP(final String ip, final String userName, final String pass) {

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                boolean status = false;
                try {
                    mFtpClient = new FTPClient();
                    //  mFtpClient.setConnectTimeout(10 * 1000);
                    mFtpClient.connect(InetAddress.getByName(ip));
                    status = mFtpClient.login(userName, pass);
                    Log.e("isFTPConnected", String.valueOf(status));
                    if (FTPReply.isPositiveCompletion(mFtpClient.getReplyCode())) {
                        mFtpClient.setFileType(FTP.ASCII_FILE_TYPE);
                        mFtpClient.enterLocalPassiveMode();
                        FTPFile[] mFileArray = mFtpClient.listFiles();
                        Log.e("Size", String.valueOf(mFileArray.length));
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();



        return mFtpClient;
    }

    */
/**
     *
     * @param ftpClient FTPclient object
     * @param downloadFile local file which need to be uploaded.
     *//*

    public static void uploadFile(FTPClient ftpClient, File downloadFile, String serverfilePath) {
        try {
            FileInputStream srcFileStream = new FileInputStream(downloadFile);
            boolean status = ftpClient.storeFile("remote ftp path",
                    srcFileStream);
            Log.e("Status", String.valueOf(status));
            srcFileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
