package com.vagwork.web;

import com.vagwork.dao.DiaryDao;
import com.vagwork.model.Diary;
import com.vagwork.model.DiaryType;
import com.vagwork.model.Page;
import com.vagwork.model.User;
import com.vagwork.service.DiaryService;
import com.vagwork.service.DiaryTypeService;
import com.vagwork.utils.DateHelper;
import com.vagwork.utils.DbUtil;
import com.vagwork.utils.PropertiesUtil;
import com.vagwork.utils.StringHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class MainServlet extends HttpServlet {
    private PropertiesUtil pu = new PropertiesUtil("diary.properties");
    private DbUtil dbUtil = new DbUtil(pu);
    private DiaryService diaryService = new DiaryService();
    private DiaryTypeService diaryTypeService = new DiaryTypeService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        Page page = new Page();
        page.setPageSize(Integer.parseInt(pu.readProperty("pageSize")));
        request.setAttribute("pageName","main");
        request.setAttribute("pageAction","");
        String all = request.getParameter("all");
        String s_diaryTitle="";
        if(StringHelper.isNotBlank(all) && "true".equals(all)){
            page.setPage(1);
            s_diaryTitle = request.getParameter("s_diaryTitle");
            if(StringHelper.isNotBlank(s_diaryTitle)){
                session.setAttribute("s_diaryTitle",s_diaryTitle);
            }else{
                session.removeAttribute("s_diaryTitle");
            }
            session.removeAttribute("s_typeId");
            session.removeAttribute("s_releaseDateStr");
        }else{
            String p = request.getParameter("page");
            if(StringHelper.isNotBlank(p) && Integer.parseInt(p) > 0){
                page.setPage(Integer.parseInt(p));
            }
        }

        if(session.getAttribute("s_diaryTitle") != null){
            s_diaryTitle = session.getAttribute("s_diaryTitle").toString();
        }
        String s_typeId="";
        if(session.getAttribute("s_typeId") != null && StringHelper.isBlank(request.getParameter("s_typeId"))){
            s_typeId = session.getAttribute("s_typeId").toString();
        }else{
            s_typeId = request.getParameter("s_typeId");
            if(StringHelper.isNotBlank(s_typeId)){
                session.removeAttribute("s_releaseDateStr");
            }
        }
        String s_releaseDateStr ="";
        if(session.getAttribute("s_releaseDateStr") != null && StringHelper.isBlank(request.getParameter("s_releaseDateStr"))){
            s_releaseDateStr = session.getAttribute("s_releaseDateStr").toString();
        }else{
            s_releaseDateStr = request.getParameter("s_releaseDateStr");
            if(StringHelper.isNotBlank(s_releaseDateStr)){
                session.removeAttribute("s_typeId");
                s_typeId="";
            }
        }
        //日志列表
        Diary diary = new Diary();
        diary.setUserId(user.getUserId());
        diary.setDiaryTitle(s_diaryTitle);
        if(StringHelper.isNotBlank(s_typeId) && Integer.parseInt(s_typeId) > 0){
            if(page.getPage() == 0){
                page.setPage(1);
            }
            diary.setDiaryTypeId(Integer.parseInt(s_typeId));
            session.setAttribute("s_typeId",s_typeId);
        }
        if(StringHelper.isNotBlank(s_releaseDateStr)){
            if(page.getPage() == 0){
                page.setPage(1);
            }
            diary.setDiaryCreateTime(DateHelper.parseString(s_releaseDateStr,"yyyy年MM月dd日"));
            session.setAttribute("s_releaseDateStr",s_releaseDateStr);
        }
        page.setTotalCount(diaryService.totalCount(diary));
        List list = diaryService.list(diary,page);
        session.setAttribute("page",page);
        session.setAttribute("diarys",list);
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
    }
}
