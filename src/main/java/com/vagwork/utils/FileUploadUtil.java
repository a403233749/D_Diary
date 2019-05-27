package com.vagwork.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import javax.servlet.ServletContext;
import java.io.File;

public class FileUploadUtil {
    //第一步 创建DiskFileItemFactory
    public  static DiskFileItemFactory newDiskFileItemFactory(ServletContext context,
                                                              File repository){
        FileCleaningTracker fileCleaningTracker
                = FileCleanerCleanup.getFileCleaningTracker(context);
        DiskFileItemFactory factory
                = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
                repository);
        factory.setFileCleaningTracker(fileCleaningTracker);
        factory.setDefaultCharset("UTF-8");
        return factory;
    }

    //第二步 创建ServletFileUpload
    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory, ProgressListener progressListener){
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        servletFileUpload.setProgressListener(progressListener);
        servletFileUpload.setHeaderEncoding("UTF-8");
        return servletFileUpload;
    }

    //第三步 默认进度监听器
    public static ProgressListener defaultProgressListener(){
        return new ProgressListener() {
            private long megaBytes = -1;
            @Override
            public void update(long pBytesRead, long pContentLength, int pItems) {
                long mBytes = pBytesRead / 1000000;
                if(megaBytes ==  mBytes){
                    return;
                }

                megaBytes = mBytes;
                System.out.println("We are currently reading item " + pItems);
                if(pContentLength == -1){
                    System.out.println("So far, " + pBytesRead + " bytes have been read.");
                }else {
                    System.out.println("So far, " + pBytesRead +" of " + pContentLength
                            + " bytes have been read.");
                }
            }
        };
    }

    //上传文件到磁盘
    public static File writeDisk(String path, String fileName, FileItem fileItem) throws Exception {
        File file = null;
        //不是表单字段
        if(!fileItem.isFormField()){
            String fieldName = fileItem.getFieldName();
            String oldFileName = fileItem.getName();
            String contentType = fileItem.getContentType();
            boolean isInMemory = fileItem.isInMemory();
            long sizeInBytes = fileItem.getSize();
            String extName = "."+StringHelper.unqualify(oldFileName);
            String newFileName = fileName + extName;
            System.out.println("processUploadedFile - oldFileName:"+fieldName+ " fileName:"+fileName
                    + " contentType:"+contentType + " isInMemory:"+isInMemory + " sizeInBytes:"+sizeInBytes + " saveFileName:" + newFileName);
            if(sizeInBytes > 0){
                file = new File(path + newFileName);
                fileItem.write(file);
            }
        }
        return file;
    }
}
