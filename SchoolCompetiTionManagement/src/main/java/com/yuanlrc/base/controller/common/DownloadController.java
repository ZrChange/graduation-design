package com.yuanlrc.base.controller.common;



import com.yuanlrc.base.bean.FileBean;
import com.yuanlrc.base.entity.admin.IndividualAccessory;
import com.yuanlrc.base.entity.admin.IndividualCompetition;
import com.yuanlrc.base.entity.admin.TeamAccessory;
import com.yuanlrc.base.entity.admin.TeamCompetition;
import com.yuanlrc.base.service.admin.IndividualAccessoryService;
import com.yuanlrc.base.service.admin.IndividualCompetitionService;
import com.yuanlrc.base.service.admin.TeamAccessoryService;
import com.yuanlrc.base.service.admin.TeamCompetitionService;
import com.yuanlrc.base.util.FileUtils;
import com.yuanlrc.base.util.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 文件下载
 */
@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private IndividualAccessoryService individualAccessoryService;

    @Autowired
    private IndividualCompetitionService individualCompetitionService;

    @Autowired
    private TeamCompetitionService teamCompetitionService;

    @Autowired
    private TeamAccessoryService teamAccessoryService;

    @Value("${ylrc.upload.photo.path}")
    private String filePath;//文件保存位置

    /* *//** * 下载文件 * * @return 成功下载文件，失败返回0 * @throws IOException */
    @GetMapping("/downloadFile")
    public void downloadFile(String fileSrc, HttpServletRequest request, HttpServletResponse response) throws IOException {
           String fileName=filePath+fileSrc;
            String s = fileName.substring(fileName.lastIndexOf("/") + 1);//文件名
        if (fileName != null) { //设置文件路径
            File file = new File(fileName); // 如果文件名存在，则进行下载
            if (file.exists()) { // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream"); // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, s)); // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i); i = bis.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                } catch (Exception e) {
                    System.out.println("Download the song failed!");
                } finally {
                    if (bis != null) {
                        try { bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    @RequestMapping(value = "/downLoadZipFile", method = RequestMethod.GET)
    public void downLoadZipFile(HttpServletResponse response, Long id) throws IOException {
        String zipName = "myfile.zip";
        List<FileBean> fileList = new ArrayList<>(); //查询数据库中记录

        IndividualCompetition individualCompetition = individualCompetitionService.find(id);
        IndividualAccessory individualAccessory = individualAccessoryService.findByIndividualCompetitionId(id);

        List<String> files = new ArrayList<>();
        if (individualAccessory != null) {

            if(individualAccessory.getPreliminariesAccessory() != null)
            {
                files.add(individualAccessory.getPreliminariesAccessory().trim());
            }

            if(individualAccessory.getSecondRoundAccessory() != null)
            {
                files.add(individualAccessory.getSecondRoundAccessory().trim());
            }

            if(individualAccessory.getFinalsAccessory() != null){
                files.add(individualAccessory.getFinalsAccessory().trim());
            }
        }

        for (String file : files) {

            String attachmentName = file.substring(file.lastIndexOf("/") + 1);//文件名

            String path = file.substring(0,file.lastIndexOf("/")+1);

            String fileUrl = filePath+path;

            FileBean fileBean = new FileBean();

            fileBean.setFileName(attachmentName);

            fileBean.setFilePath(fileUrl);

            fileList.add(fileBean);
        }

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for (Iterator<FileBean> it = fileList.iterator(); it.hasNext(); ) {
                FileBean file = it.next();
                ZipUtils.doCompress(file.getFilePath() + file.getFileName(), out);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @RequestMapping(value = "/downLoadZipFileTeam", method = RequestMethod.GET)
    public void downLoadZipFileTeam(HttpServletResponse response, Long id) throws IOException {
        String zipName = "myfile.zip";
        List<FileBean> fileList = new ArrayList<>(); //查询数据库中记录

        TeamCompetition teamCompetition = teamCompetitionService.find(id);
        TeamAccessory teamAccessory = teamAccessoryService.findByTeamCompetitionId(id);

        List<String> files = new ArrayList<>();
        if (teamAccessory != null) {

            if(teamAccessory.getPreliminariesAccessory() != null)
            {
                files.add(teamAccessory.getPreliminariesAccessory().trim());
            }

            if(teamAccessory.getSecondRoundAccessory() != null)
            {
                files.add(teamAccessory.getSecondRoundAccessory().trim());
            }

            if(teamAccessory.getFinalsAccessory() != null){
                files.add(teamAccessory.getFinalsAccessory().trim());
            }
        }

        for (String file : files) {

            String attachmentName = file.substring(file.lastIndexOf("/") + 1);//文件名

            String path = file.substring(0,file.lastIndexOf("/")+1);

            String fileUrl = filePath+path;

            FileBean fileBean = new FileBean();

            fileBean.setFileName(attachmentName);

            fileBean.setFilePath(fileUrl);

            fileList.add(fileBean);
        }

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for (Iterator<FileBean> it = fileList.iterator(); it.hasNext(); ) {
                FileBean file = it.next();
                ZipUtils.doCompress(file.getFilePath() + file.getFileName(), out);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
