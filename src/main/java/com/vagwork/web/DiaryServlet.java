package com.vagwork.web;

import com.vagwork.model.Diary;
import com.vagwork.model.DiaryType;
import com.vagwork.model.Page;
import com.vagwork.model.User;
import com.vagwork.service.DiaryService;
import com.vagwork.service.DiaryTypeService;
import com.vagwork.utils.PropertiesUtil;
import com.vagwork.utils.StringHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DiaryServlet extends HttpServlet {
    private PropertiesUtil pu = new PropertiesUtil("diary.properties");
    private DiaryService diaryService = new DiaryService();
    private DiaryTypeService diaryTypeService = new DiaryTypeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(StringHelper.isNotBlank(action)) {
            HttpSession session = request.getSession();
            session.removeAttribute("error");
            User user = (User) session.getAttribute("currentUser");

            if ("preSave".equals(action)) {
                Diary diary = handleDiary(request);
                diary.setUserId(user.getUserId());
                if(diary.getDiaryId() > 0){
                    diary = diaryService.getOne(diary);
                }
                session.setAttribute("diary",diary);
                request.setAttribute("pageName", "diary");
                request.setAttribute("pageAction", "preSave");
            } else if("save".equals(action)){ //保存和更新
                Diary diary = handleDiary(request);
                String errorMsg = "";
                if(StringHelper.isBlank(diary.getDiaryTitle())){
                    errorMsg = "日记标题不能为空";
                }
                else if(StringHelper.isBlank(diary.getDiaryContent())){
                    errorMsg = "日记内容不嫩为空";
                }
                else if(diary.getDiaryTypeId() == 0){
                    errorMsg = "日记类型不嫩为空";
                }
                if(StringHelper.isNotBlank(errorMsg)){
                    session.setAttribute("error",errorMsg);
                    session.setAttribute("diary",diary);
                    request.setAttribute("pageName", "diary");
                    request.setAttribute("pageAction", "preSave");
                    request.getRequestDispatcher("main.jsp").forward(request,response);
                    return;
                }else {
                    session.removeAttribute("error");
                    diary.setUserId(user.getUserId());
                    diaryService.save(diary);
                    request.setAttribute("pageName", "main");
                    request.setAttribute("pageAction", "");
                }
            } else if("show".equals(action)) {
                Diary diary = handleDiary(request);
                diary.setUserId(user.getUserId());
                if(diary.getDiaryId() > 0){
                    diary = diaryService.getOne(diary);
                    session.setAttribute("diary",diary);

                    DiaryType diaryType = new DiaryType();
                    diaryType.setUserId(user.getUserId());
                    diaryType.setDiaryTypeId(diary.getDiaryTypeId());
                    diaryType = diaryTypeService.getOne(diaryType);
                    session.setAttribute("diaryType",diaryType);

                    request.setAttribute("pageName","diary");
                    request.setAttribute("pageAction","show");
                }
                String diaryId = request.getParameter("diaryId");
            } else if("delete".equals(action)) {
                Diary diary = handleDiary(request);
                diary.setUserId(user.getUserId());
                if(diary.getDiaryId() > 0){
                    diaryService.delete(diary);
                }

                request.setAttribute("pageName", "main");
                request.setAttribute("pageAction", "");
            }
            //日记列表
            Diary diary = new Diary();
            diary.setUserId(user.getUserId());
            Page page = new Page();
            page.setPage(1);
            page.setPageSize(Integer.parseInt(pu.readProperty("pageSize")));
            page.setTotalCount(diaryService.totalCount(diary));
            diary.setUserId(user.getUserId());
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
        }
        request.getRequestDispatcher("main.jsp").forward(request,response);
    }

    /**
     * 处理request数据 封装成diary对象
     * @param request
     * @return
     */
    private Diary handleDiary(HttpServletRequest request){
        Diary rd = new Diary();
        String diaryId = request.getParameter("diaryId");
        String diaryTitle = request.getParameter("diaryTitle");
        String diaryContent = request.getParameter("diaryContent");
        String diaryTypeId = request.getParameter("diaryTypeId");

        if(StringHelper.isBlank(diaryId)){
            rd.setDiaryId(0);
        }else{
            rd.setDiaryId(Integer.parseInt(diaryId));
        }
        rd.setDiaryTitle(diaryTitle);
        rd.setDiaryContent(diaryContent);
        if(StringHelper.isBlank(diaryTypeId)){
            rd.setDiaryTypeId(0);
        }else {
            rd.setDiaryTypeId(Integer.parseInt(diaryTypeId));
        }
        return rd;
    }
}
