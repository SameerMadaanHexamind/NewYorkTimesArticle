package com.scorematics.android.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static android.os.Environment.MEDIA_MOUNTED;


/*
 * ************************************************************************************************************************************************
 *
 * This is a reusable Utility class for File functions. The code has been done here and can be used multiple times in the project
 *
 * @author
 *
 *******************************************************************************************************************************************
 */

public final class FileUtils {

    /**
     * Variables
     */
    private static String TAG = FileUtils.class.getSimpleName();
    private static FileUtils instance;
    /**
     * int variables
     */
    private int BUFFER = 2048;
    private final static String FILE_NAME_IMAGE = "IMG_";
    private final static String FILE_NAME_VIDEO = "VID_";
    private final static String FILE_NAME_CAMERA_IMAGE = "IMG_CAMERA_";
    public static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final boolean DEBUG = false; // Set to true to enable logging
    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    public static final String MIME_TYPE_APP = "application/*";
    public final static String FILE_EXTENSION_SEPARATOR = ".";
    public static final String HIDDEN_PREFIX = ".";

    private FileUtils() {
    }

    public static synchronized FileUtils getInstance() {

        return instance == null ? instance = new FileUtils()
                : instance;
    }


    /**
     * This method is used to copy file from srcFile to destFile
     *
     * @param srcFile  set source file
     * @param destFile set destination file
     * @throws IOException thorws expection
     */
    public void copyfile(File srcFile, File destFile) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
    }

    /**
     * This method is used to copy InputStream file to the sdcard file
     *
     * @param inputstream set inputstream
     * @param file        set file path where data has to save.
     */
    public void copyInputStreamToFile(InputStream inputstream, File file) {
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputstream, file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * This method is used to convert bitmap to byteArray
     *
     * @param bitmap bitmap to convert into byte array
     * @return byteArray
     */
    public byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }


    /**
     * to decompress file zip
     *
     * @param zipFile     the target ZIP file to unzip
     * @param pathToUnzip where unzipped file to be stored
     */
    public void decompress(String zipFile, String pathToUnzip) {

        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(
                    zipFile));
            ZipEntry entry = null;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    FileOutputStream fout = new FileOutputStream(pathToUnzip
                            + "/" + entry.getName());
                    for (int c = zipIn.read(); c != -1; c = zipIn.read()) {
                        fout.write(c);
                    }
                    zipIn.closeEntry();
                    fout.close();
                }
            }

            zipIn.close();
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    /**
     * to compress file
     *
     * @param file    the input file to compress
     * @param zipFile name of the compressed file
     */
    public void compress(String file, String zipFile) {
        try {

            BufferedInputStream inBufferStream = new BufferedInputStream(
                    new FileInputStream(file), BUFFER);
            FileOutputStream targetOutputStream = new FileOutputStream(zipFile);
            ZipOutputStream outZipStream = new ZipOutputStream(
                    new BufferedOutputStream(targetOutputStream));

            byte data[] = new byte[BUFFER];

            ZipEntry entry = new ZipEntry(
                    file.substring(file.lastIndexOf("/") + 1));
            outZipStream.putNextEntry(entry);
            int count;
            while ((count = inBufferStream.read(data, 0, BUFFER)) != -1) {
                outZipStream.write(data, 0, count);
            }

            inBufferStream.close();
            outZipStream.close();

        } catch (Exception ex) {

        }
    }


    /**
     * Functionality to get directory name file
     *
     * @return directory file where the all the application's files are stored
     */

    public File getDirectoryApp(Context context) {
        String cacheFilePath = Environment.getExternalStorageDirectory()
                .getPath() + "/Android/" + context.getPackageName();
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && hasExternalStoragePermission(context)) {
            appCacheDir = new File(cacheFilePath);
        }
        if (appCacheDir == null
                || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        Log.d(TAG, "directory path = "
                + appCacheDir);

        return appCacheDir;
    }

    /**
     * Functionality to get directory of images cache file
     *
     * @return directory file where the all the application's images are stored
     */

    public File getDirectoryAppCacheImages(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/CacheImages");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        Log.d(TAG, "image directory path = "
                + dir);

        return dir;
    }

    /**
     * Functionality to get directory of images file
     *
     * @return directory file where the all the application's images are stored
     */

    public File getDirectoryAppImages(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/Images");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        Log.d(TAG, "image directory path = "
                + dir);

        return dir;
    }

    /**
     * Functionality to get directory of images file
     *
     * @return directory file where the all the application's images are stored
     */

    public File getDirectoryAppVideos(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/Videos");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        Log.d(TAG, "video directory path = "
                + dir);

        return dir;
    }


    public File getStaticFile(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            name = FILE_NAME_IMAGE + "temp.jpeg";
        } else if (!name.endsWith(".jpeg")) {
            name = name + ".jpeg";
        }
        File dir = new File(getDirectoryApp(context),
                "/cache");
        File filePath = new File(dir, name);
        return filePath;

    }

    public File getStaticVideoFile(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            name = "temp_video.mp4";
        } else if (!name.endsWith(".mp4")) {
            name = name + ".mp4";
        }
        File dir = new File(getDirectoryApp(context),
                "/cache");
        File filePath = new File(dir, name);
        return filePath;
    }

    public void writeStringToFile(File file, String data) {
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStringToFile(File file, String data, boolean append) {
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, data, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File src, File dst) {
        try {
            org.apache.commons.io.FileUtils.copyFile(src, dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasExternalStoragePermission(Context context) {
        int perm = context
                .checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Functionality to search files and folders from the mobile device either
     * from internal or from external
     *
     * @param fileName file name or folder name from that all files are fetched
     * @return array list containing path of data
     * @permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
     */
    public ArrayList<String> findFilesMain(String fileName) {
        ArrayList<String> folderNameList = new ArrayList<String>();
        ArrayList<String> fileList = new ArrayList<String>();
        File file = new File("/");
        File folderList[] = file.listFiles();
        if (folderList != null && folderList.length > 0) {
            for (int i = 0; i < folderList.length; i++) {
                File folderList1[] = folderList[i].listFiles();
                if (folderList1 != null && folderList1.length > 0) {
                    findFilesPath(folderList1, folderNameList, fileList,
                            fileName);
                } else {
                    Log.d(TAG,
                            "file name " + folderList[i].getName());
                }
            }
        }


        return fileList;
    }

    /**
     * Functionality to find particular folder/file from the device
     *
     * @param folderList array of files/folders
     * @param fileList   path list on which the path of searched data will also be
     *                   added
     * @param fileName   file name or folder name from that all files are fetched
     */
    private void findFilesPath(File folderList[],
                               ArrayList<String> folderNameList,
                               ArrayList<String> fileList, String fileName) {
        if (folderList != null && folderList.length > 0) {
            for (int i = 0; i < folderList.length; i++) {
                File folderList1[] = folderList[i].listFiles();
                if (folderList1 != null && folderList1.length > 0) {
                    if (!folderNameList.contains(folderList[i].getName())) {
                        if (folderList[i].getName().contains(fileName)) {
                            File fileLists[] = folderList[i].listFiles();
                            if (fileList != null && fileLists.length > 0) {
                                for (int j = 0; j < fileLists.length; j++) {
                                    getFileList(fileLists, fileList);

                                }

                            }
                        } else {
                            folderNameList.add(folderList[i].getName());
                            findFilesPath(folderList1, folderNameList,
                                    fileList, fileName);
                        }
                    }
                } else {
                    Log.d(TAG + " File Handling",
                            "file name " + folderList[i].getName());
                }
            }
        }
    }

    /**
     * @param folderList array of files/folders
     * @param list       path list on which the path of searched data will also be
     *                   added
     */
    private void getFileList(File folderList[],
                             ArrayList<String> list) {
        for (int i = 0; i < folderList.length; i++) {
            if (folderList[i].getName().contains(".db")) {
                if (!TextUtils.isEmpty(folderList[i].getPath())) {
                    list.add(folderList[i].getPath());
                }
                Log.d(TAG, "file name " + folderList[i].getName()
                        + " path = " + folderList[i].getPath());
            }
            File fileList[] = folderList[i].listFiles();
            if (fileList != null && fileList.length > 0) {

                for (int j = 0; j < fileList.length; j++) {
                    getFileList(fileList, list);
                }

            }
        }
    }


    /**
     * Functionality to convert a size into byte, kilobyte, megabyte, gigabyte or terabyte
     * {@link <ahref http://stackoverflow.com/a/5599842/>}
     */
    public String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public int defineExifOrientation(String imageUri) {
        int rotation = 0;

        try {
            ExifInterface exif = new ExifInterface(imageUri);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (exifOrientation) {

                case ExifInterface.ORIENTATION_NORMAL:
                    rotation = 0;

                    break;

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;

                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;

                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;

                    break;
            }
        } catch (IOException e) {
            Log.e("Can't read", imageUri);
        }

        return rotation;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * This method is used to scan sdcard programatically to update the values
     *
     * @param filePath set filePath to scan
     */
    public void scanMedia(Context context, String filePath) {
        MediaScannerConnection.scanFile(context, new String[]{filePath},
                null, null);
    }




    /**
     * This method is used to write data on file. It will check for the media
     * mounted and write external storage permission before writing the file to
     * SDcard.
     *
     *
     * @return file path in String format where the file has been written
     * successfully else return null.
     */


/*

    public String writetoFile(Context context, String filePath, String fileName,
                              InputStream inputStream) */
/*{
        File expPath = null;
        if (ApplicationUtils.getInstance().isMediaMounted()) {
            if (NetworkUtils.getInstance(context).checkPermissions(context, ApplicationUtils.BasicPermissions.WRITE_PERMISSION)) {
                try {
                    File root = Environment.getExternalStorageDirectory();
                    if (filePath != null && fileName != null) {
                        expPath = new File(root.toString() + "/" + filePath,
                                fileName);
                        // / if (!expPath.exists()) {
                        if (inputStream != null) {
                            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream,
                                    expPath);
                        }
                        // }
                    }
                    return expPath.getPath();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                ToastUtils.getInstance().toast(context,
                        "Write External Storage permission is not available."
                );
            }
        } else {
            ToastUtils.getInstance().toast(context, "No External media found to write the file");
        }
        return null;
    }*//*



    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * @return Whether the URI is a local one.
     */
    public boolean isLocal(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return true;
        }
        return false;
    }

    /**
     * @return True if Uri is a MediaStore Uri.
     * @author paulburke
     */
    public boolean isMediaUri(Uri uri) {
        return "media".equalsIgnoreCase(uri.getAuthority());
    }

    /**
     * Convert File into Uri.
     *
     * @param file
     * @return uri
     */
    public Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    /**
     * Returns the path only (without file name).
     *
     * @param file
     * @return
     */
    public File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }

    /**
     * @return The MIME type for the given file.
     */
    public String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * Get the file size in a human-readable string.
     *
     * @param size
     * @return
     * @author paulburke
     */
    public String getReadableFileSize(int size) {
        final int BYTES_IN_KILOBYTES = 1024;
        final DecimalFormat dec = new DecimalFormat("###.#");
        final String KILOBYTES = " KB";
        final String MEGABYTES = " MB";
        final String GIGABYTES = " GB";
        float fileSize = 0;
        String suffix = KILOBYTES;

        if (size > BYTES_IN_KILOBYTES) {
            fileSize = size / BYTES_IN_KILOBYTES;
            if (fileSize > BYTES_IN_KILOBYTES) {
                fileSize = fileSize / BYTES_IN_KILOBYTES;
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize = fileSize / BYTES_IN_KILOBYTES;
                    suffix = GIGABYTES;
                } else {
                    suffix = MEGABYTES;
                }
            }
        }
        return String.valueOf(dec.format(fileSize) + suffix);
    }

    /**
     * Attempt to retrieve the thumbnail of given File from the MediaStore. This
     * should not be called on the UI thread.
     *
     * @param
     * @return
     * @author paulburke
     */
    public Bitmap getThumbnail(Context context, File file) {
        return getThumbnail(context, getUri(file), getMimeType(file));
    }


    /**
     * Attempt to retrieve the thumbnail of given Uri from the MediaStore. This
     * should not be called on the UI thread.
     *
     * @param uri
     * @param mimeType
     * @return
     * @author paulburke
     */
    public Bitmap getThumbnail(Context context, Uri uri, String mimeType) {
        if (DEBUG)
            Log.d(TAG, "Attempting to get thumbnail");

        if (!isMediaUri(uri)) {
            Log.e(TAG, "You can only retrieve thumbnails for images and videos.");
            return null;
        }

        Bitmap bm = null;
        if (uri != null) {
            final ContentResolver resolver = context.getContentResolver();
            Cursor cursor = null;
            try {
                cursor = resolver.query(uri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    final int id = cursor.getInt(0);
                    if (DEBUG)
                        Log.d(TAG, "Got thumb ID: " + id);

                    if (mimeType.contains("video")) {
                        bm = MediaStore.Video.Thumbnails.getThumbnail(
                                resolver,
                                id,
                                MediaStore.Video.Thumbnails.MINI_KIND,
                                null);
                    } else if (mimeType.contains(FileUtils.MIME_TYPE_IMAGE)) {
                        bm = MediaStore.Images.Thumbnails.getThumbnail(
                                resolver,
                                id,
                                MediaStore.Images.Thumbnails.MINI_KIND,
                                null);
                    }
                }
            } catch (Exception e) {
                if (DEBUG)
                    Log.e(TAG, "getThumbnail", e);
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return bm;
    }

    /**
     * File and folder comparator. TODO Expose sorting option method
     *
     * @author paulburke
     */
    public Comparator<File> sComparator = new Comparator<File>() {
        @Override
        public int compare(File f1, File f2) {
            // Sort alphabetically by lower case, which is much cleaner
            return f1.getName().toLowerCase().compareTo(
                    f2.getName().toLowerCase());
        }
    };

    /**
     * File (not directories) filter.
     *
     * @author paulburke
     */
    public FileFilter sFileFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return files only (not directories) and skip hidden files
            return file.isFile() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };

    /**
     * Folder (directories) filter.
     *
     * @author paulburke
     */
    public FileFilter sDirFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return directories only and skip hidden directories
            return file.isDirectory() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };

    /**
     * Get the Intent for selecting content to be used in an Intent Chooser.
     *
     * @return The intent for opening a file with Intent.createChooser()
     * @author paulburke
     */
    public Intent createGetContentIntent() {
        // Implicitly allow the user to select a particular kind of data
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // The MIME data type filter
        intent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    public String getRealPath(Context context, Uri uri) {
        String filePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // only for Kitkat and newer versions
            filePath = getRealPathFromURI_API19(context, uri);
        } else {
            filePath = getRealPathFromURI_API11to18(context, uri);
        }
        return filePath;
    }



    public String readTextFileFromAssets(Context context, String assetFileName) {
        StringBuilder total = new StringBuilder();
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(assetFileName);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total.toString();
    }



    /**
     * get file name from path, not include suffix
     * <p>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * get file name from path, include suffix
     * <p>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * get folder name from path
     * <p>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * get suffix of file from path
     * <p>
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * Creates the directory named by the trailing filename of this file, including the complete directory path required
     * to create this directory. <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target directory already exists, false one of
     * the directories can not be created.
     * <ul>
     * <li>if {@link FileUtils#getFolderName(String)} return null, return false</li>
     * <li>if target directory already exists, return true</li>
     * </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * @param filePath
     * @return
     * @see #makeDirs(String)
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * get file size
     * <ul>
     * <li>if path is null or empty, return -1</li>
     * <li>if path exist and it is a file, return file size, else return -1</li>
     * <ul>
     *
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file does not exist.
     */
    public long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }

        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    @SuppressLint("NewApi")
    public String getRealPathFromURI_API19(Context context, Uri photoUri){
        String path = "";
        if (DocumentsContract.isDocumentUri(context, photoUri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(photoUri)) {
                final String docId = DocumentsContract.getDocumentId(photoUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(photoUri)) {

                final String id = DocumentsContract.getDocumentId(photoUri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                path = getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(photoUri)) {
                final String docId = DocumentsContract.getDocumentId(photoUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                path = getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(photoUri.getScheme())) {
            path = getDataColumn(context, photoUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(photoUri.getScheme())) {
            path = photoUri.getPath();
        }
        return path;
    }


    @SuppressLint("NewApi")
    public String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        Log.d(TAG, "API 11 to 18");
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        Log.d(TAG, "API Below 11");
        String path = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        path = cursor.getString(column_index);
        cursor.close();
        return path;
    }




}
