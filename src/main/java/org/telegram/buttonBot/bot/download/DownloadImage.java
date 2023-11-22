package org.telegram.buttonBot.bot.download;

import org.apache.commons.io.FileUtils;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.InputStream;

import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.openInputStream;

public class DownloadImage {

    public static void run(Document document, Update update) throws TelegramApiException {


            String doc_id = document.getFileId();
//            String doc_name = document.getFileName()
            String doc_mine = document.getMimeType();
            long doc_size = document.getFileSize();

            String getid = String.valueOf(DataBase.chatId);



//            document document = new document();
//            document.setmimetype(doc_mine);
//            document.setfilename(doc_name);
//            document.setfilesize(doc_size);
//            document.setfileid(doc_id);

            GetFile getfile = new GetFile();
            getfile.setFileId(document.getFileId());






    }
}
