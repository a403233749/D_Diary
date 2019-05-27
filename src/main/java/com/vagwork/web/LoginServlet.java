package com.vagwork.web;

import com.vagwork.model.Diary;
import com.vagwork.model.DiaryType;
import com.vagwork.model.Page;
import com.vagwork.model.User;
import com.vagwork.service.DiaryService;
import com.vagwork.service.DiaryTypeService;
import com.vagwork.service.UserService;
import com.vagwork.utils.PropertiesUtil;
import com.vagwork.utils.StringHelper;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private PropertiesUtil pu = new PropertiesUtil("diary.properties");
    private UserService userService = new UserService();
    private DiaryService diaryService = new DiaryService();
    private DiaryTypeService diaryTypeService = new DiaryTypeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember-me");


        String errorMsg = "";
        if(StringHelper.isBlank(userName) || StringHelper.isBlank(password)){
            errorMsg = "用户名或密码不能为空";
        }
        if(StringHelper.isNotBlank(errorMsg)){
            request.setAttribute("userName",userName);
            request.setAttribute("password",password);
            request.setAttribute("error",errorMsg);
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return;
        }

        //设置cookie
        if(StringHelper.isNotBlank(rememberMe) && "remember-me".equals(rememberMe)){
            Cookie userNameAndPwd = new Cookie("userNameAndPwd",userName+"-"+password);
            userNameAndPwd.setMaxAge(1*60*60*24*7);
            response.addCookie(userNameAndPwd);
            System.out.println("设置cookie成功");
        } else {
            //删除cookie
            Cookie[] cookies = request.getCookies();
            for(int i=0;cookies != null && i <cookies.length;i++){
                if("userNameAndPwd".equals(cookies[i].getName())){
                    cookies[i].setMaxAge(0);
                    response.addCookie( cookies[i]);
                    System.out.println("删除cookie成功");
                }
            }
        }
        HttpSession session = request.getSession();

        User user = new User(userName,password);
        user = userService.login(user);
        //登录
        if(user != null && user.getUserId()>0){//登录成功
            session.setAttribute("currentUser", user);
            session.setAttribute("imageFile",pu.readProperty("imageFile"));

            Diary diary = new Diary();
            diary.setUserId(user.getUserId());
            //分页信息
            Page page=new Page();
            page.setPage(1);
            page.setPageSize(Integer.parseInt(pu.readProperty("pageSize")));
            page.setTotalCount(diaryService.totalCount(diary));
            session.setAttribute("page",page);
            //日记列表
            List diarys = diaryService.list(diary,page);
            session.setAttribute("diarys",diarys);
            //diaryCreateTime列表
            List diaryCreateTimes = diaryService.getGroupDiaryCrateTime(diary);
            session.setAttribute("diaryCreateTimes",diaryCreateTimes);
            //日记类型列表
            DiaryType diaryType = new DiaryType();
            diaryType.setUserId(user.getUserId());
            List<DiaryType> diaryTypes = diaryTypeService.list(diaryType,null);
            for(int i=0;i<diaryTypes.size();i++){
                Diary s_d = new Diary();
                s_d.setUserId(user.getUserId());
                s_d.setDiaryTypeId(diaryTypes.get(i).getDiaryTypeId());
                int total = diaryService.totalCount(s_d);
                diaryTypes.get(i).setDiaryCount(total);
            }
            session.setAttribute("diaryTypes",diaryTypes);
            request.getRequestDispatcher("main.jsp").forward(request,response);
        }else{
            request.setAttribute("userName",userName);
            request.setAttribute("password",password);
            request.setAttribute("rememberMe",rememberMe);
            request.setAttribute("error","登录失败，用户名或密码错误");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
}
