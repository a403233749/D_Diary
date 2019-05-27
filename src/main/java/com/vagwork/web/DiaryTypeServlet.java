package com.vagwork.web;

import com.vagwork.model.Diary;
import com.vagwork.model.DiaryType;
import com.vagwork.model.User;
import com.vagwork.service.DiaryService;
import com.vagwork.service.DiaryTypeService;
import com.vagwork.utils.StringHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DiaryTypeServlet extends HttpServlet {
    private DiaryService diaryService = new DiaryService();
    private DiaryTypeService diaryTypeService = new DiaryTypeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if(StringHelper.isNotBlank(action)) {
            HttpSession session=request.getSession();
            User user = (User) session.getAttribute("currentUser");
            session.removeAttribute("error");
            if("preSave".equals(action)){
                DiaryType diaryType = handleDiaryType(request);
                diaryType.setUserId(user.getUserId());
                if(diaryType.getDiaryTypeId() > 0){
                    diaryType = diaryTypeService.getOne(diaryType);
                }
                session.setAttribute("diaryType",diaryType);
                request.setAttribute("pageName","diaryType");
                request.setAttribute("pageAction","preSave");
                request.getRequestDispatcher("main.jsp").forward(request,response);
                return;
            } else if ("save".equals(action)){
                DiaryType diaryType = handleDiaryType(request);
                String errorMsg = "";
                if(StringHelper.isBlank(diaryType.getDiaryTypeName())){
                    errorMsg = "日记类别名称不能为空";
                }
                if(StringHelper.isNotBlank(errorMsg)){
                    session.setAttribute("error",errorMsg);
                    session.setAttribute("diaryType",diaryType);
                    request.setAttribute("pageName","diaryType");
                    request.setAttribute("pageAction","preSave");
                    request.getRequestDispatcher("main.jsp").forward(request,response);
                    return;
                } else {
                    session.removeAttribute("error");
                    diaryType.setUserId(user.getUserId());
                    diaryTypeService.save(diaryType);
                }
            } else if ("delete".equals(action)){
                DiaryType diaryType = handleDiaryType(request);
                diaryType.setUserId(user.getUserId());
                if(diaryType.getDiaryTypeId() > 0){
                    Diary s_d = new Diary();
                    s_d.setUserId(user.getUserId());
                    s_d.setDiaryTypeId(diaryType.getDiaryTypeId());
                    if(diaryService.totalCount(s_d) > 0){
                        session.setAttribute("error","删除失败，请移动或删除日记类别下的日记");
                    }
                    else{
                        diaryTypeService.delete(diaryType);
                    }
                }
            }
            request.setAttribute("pageName","diaryType");
            request.setAttribute("pageAction","list");

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
        }
        request.getRequestDispatcher("main.jsp").forward(request,response);
    }

    /**
     * 处理request数据 封装成diaryType对象
     * @param request
     * @return
     */
    private DiaryType handleDiaryType(HttpServletRequest request){
        DiaryType rdt = new DiaryType();
        String diaryTypeId = request.getParameter("diaryTypeId");
        String diaryTypeName = request.getParameter("diaryTypeName");

        if(StringHelper.isBlank(diaryTypeId)){
            rdt.setDiaryTypeId(0);
        }else {
            rdt.setDiaryTypeId(Integer.parseInt(diaryTypeId));
        }
        rdt.setDiaryTypeName(diaryTypeName);
        return rdt;
    }
}
