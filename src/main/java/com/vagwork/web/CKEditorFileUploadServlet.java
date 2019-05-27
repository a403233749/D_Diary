package com.vagwork.web;

import com.vagwork.model.User;
import com.vagwork.utils.FileUploadUtil;
import com.vagwork.utils.PropertiesUtil;
import com.vagwork.utils.StringHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CKEditorFileUploadServlet extends HttpServlet {
    PropertiesUtil pu = new PropertiesUtil("diary.properties");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            //上下文
            ServletContext servletContext = this.getServletConfig().getServletContext();
            //临时目录
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            //为基于磁盘的文件项创建工厂
            DiskFileItemFactory factory = FileUploadUtil.newDiskFileItemFactory(servletContext,repository);
            //文件上传处理程序
            ServletFileUpload upload = FileUploadUtil.getServletFileUpload(factory,FileUploadUtil.defaultProgressListener());
            //CKEditor上传回调
            String callback = request.getParameter("CKEditorFuncNum");
            try {
                out = response.getWriter();
                //解析请求
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if(!item.isFormField()) { //处理文件上传
                        //上传文件路径
                        String path = this.getServletConfig().getServletContext().getRealPath("/") + pu.readProperty("imageFile");
                        //新文件名
                        StringBuilder sb = new StringBuilder("image-").append(UUID.randomUUID());
                        File file = FileUploadUtil.writeDisk(path,sb.toString(),item);
                        if(file != null){
                            out.println("<script type=\"text/javascript\">");
                            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
                                    + ",'" + request.getContextPath() + "/" + pu.readProperty("imageFile") + sb.toString() + "','')");
                            out.println("</script>");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件上传失败！');");
                out.println("</script>");
            }
        }
    }
}
