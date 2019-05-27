package com.vagwork.web;

import com.vagwork.model.User;
import com.vagwork.service.UserService;
import com.vagwork.utils.DateHelper;
import com.vagwork.utils.FileUploadUtil;
import com.vagwork.utils.PropertiesUtil;
import com.vagwork.utils.StringHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UserServlet extends HttpServlet {
    PropertiesUtil pu = new PropertiesUtil("diary.properties");
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        String action = request.getParameter("action");
        if(StringHelper.isNotBlank(action)){
            if("preSave".equals(action)){
                request.setAttribute("pageName","user");
                request.setAttribute("pageAction","preSave");
                request.getRequestDispatcher("main.jsp").forward(request,response);
                return;
            } else if("save".equals(action)){
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                String imageName = "";
                if(user != null){
                    if(isMultipart){
                        //上下文
                        ServletContext servletContext = this.getServletConfig().getServletContext();
                        //临时目录
                        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                        //为基于磁盘的文件项创建工厂
                        DiskFileItemFactory factory = FileUploadUtil.newDiskFileItemFactory(servletContext,repository);
                        //文件上传处理程序
                        ServletFileUpload upload = FileUploadUtil.getServletFileUpload(factory,FileUploadUtil.defaultProgressListener());
                        //解析请求
                        try {
                            List<FileItem> items = upload.parseRequest(request);
                            Iterator<FileItem> iter = items.iterator();
                            while(iter.hasNext()){
                                FileItem item = iter.next();
                                if(!item.isFormField()) { //处理文件上传
                                    //上传文件路径
                                    String path = this.getServletConfig().getServletContext().getRealPath("/") + pu.readProperty("imageFile");
                                    String fileName = user.getUserName()+"_"+ DateHelper.formatDate(new Date(),"yyyyMMddhhmmss");
                                    File file = FileUploadUtil.writeDisk(path,fileName,item);
                                    if(file != null){
                                        imageName = file.getName();
                                        if(!"default.jpg".equals(user.getImageName())){
                                            File oldFile = new File(path + user.getImageName());
                                            if(oldFile != null){
                                                oldFile.delete();
                                            }
                                        }

                                    }
                                }else {//处理表单
                                    String nickName = "";
                                    if("nickName".equals(item.getFieldName())){
                                        nickName = item.getString();
                                    }
                                    String mood = "";
                                    if("mood".equals(item.getFieldName())){
                                        mood = item.getString();
                                    }
                                    if(StringHelper.isNotBlank(nickName)){
                                        user.setNickName(nickName);
                                    }
                                    if(StringHelper.isNotBlank(mood)){
                                        user.setMood(mood);
                                    }else{
                                        user.setMood("未填写");
                                    }
                                    if(StringHelper.isNotBlank(imageName)){
                                        user.setImageName(imageName);
                                    }
                                }
                            }
                            userService.save(user);
                            session.setAttribute("currentUser",user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        request.setAttribute("pageName","main");
        request.setAttribute("pageAction","");
        request.getRequestDispatcher("main.jsp").forward(request,response);
    }
}
