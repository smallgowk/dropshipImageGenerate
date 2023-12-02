/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.run;

import com.api.ImgurApi;
import static com.config.Configs.CONFIG_FOLDER_PATH;
import static com.config.Configs.filePath;
import com.google.common.base.CharMatcher;
import com.controller.crawl.aliex.AliexCrawlSvs;
import com.controller.imgur.ImgurUtils;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.filerequests.FileRequest;
import com.dropbox.core.v2.files.DbxUserListFolderBuilder;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.SearchMatchV2;
import com.dropbox.core.v2.files.SearchV2Result;
import com.dropbox.core.v2.files.SharedLink;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksBuilder;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.RequestedLinkAccessLevel;
import com.dropbox.core.v2.sharing.SharedFolderMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import com.dropbox.core.v2.users.FullAccount;
import com.models.imgur.Album;
import com.utils.AWSUtil;
import static com.utils.AWSUtil.BRAND_NAME_FILE;
import static com.utils.AWSUtil.addForWithKeyIndex;
import static com.utils.AWSUtil.removeWithKeyIndex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import org.jsoup.nodes.Document;
import static com.utils.AWSUtil.getListKeywordIndex;
import com.utils.PhantomJsManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.imageio.ImageIO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Admin
 */
public class TestImgur {

    private static final RuntimeException NOT_VALID_EAN_EXCEPTION = new RuntimeException("NOT VALID EAN CODE");

    static final long MAX_POSSIBLE_UPC_CODE = 999999999999L;
    static final long MIN_POSSIBLE_UPC_CODE = 100000000000L;

    static String id = "32972271832";

    static int count = 0;
    static int size = 0;
    static long start = 0;
    static long end = 0;

    static long startOne = 0;
    static long endOne = 0;

    static long apiTimeTotal = 0;
    static long crawlTimeTotal = 0;
    


    public static void main(String[] str) throws Exception {
        long start = System.currentTimeMillis();
//        byte[] fileContent = FileUtils.readFileToByteArray(new File("C:\\Users\\PhanDuy\\Desktop\\Test\\DataNeedChangeUrl_TestTool\\26227557.jpg"));
//        String encodedString = Base64.getEncoder().encodeToString(fileContent);
//        System.out.println("");
        
//     BufferedImage sourceimage = ImageIO.read(new File("C:\\Users\\PhanDuy\\Desktop\\Test\\DataNeedChangeUrl_TestTool\\26227557.jpg"));
//     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//     ImageIO.write(sourceimage, "png", bytes);
//     String resultantimage = Base64.encode(bytes.toByteArray());
////     System.out.println(resultantimage);
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
////                .addFormDataPart("image", "https://ae01.alicdn.com/kf/S6850a865fc464e9d8ff8253ab2588458m/Women-s-Basketball-Shoes-Fashion-Sports-Shoes-Hot-Couple-Shoes-Cushioning-Breathable-Street-Basketball-Training-Boots.jpg")
//                .addFormDataPart("image", resultantimage)
////                .addFormDataPart("type", "url")
////                .addFormDataPart("name", "test.jpg")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://api.imgur.com/3/image")
//                .method("POST", body)
//                .addHeader("Authorization", "Client-ID ce619cb16ca344c")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println("" + response.body().string());
//        System.out.println("" + (System.currentTimeMillis() - start));
        ImgurApi.generateToken();
        ArrayList<Album> listAlbums = ImgurApi.getAllAlbums();
        String albumName = "TestAlbum";
        if (ImgurUtils.findAlbum(listAlbums, albumName) == null) {
            ImgurApi.createAlbum(albumName);
            System.out.println("Created album " + albumName);
        } else {
            System.out.println("Existed album!");
        }
    }
    
    

    private static String toString(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }

    private static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            return env.get("COMPUTERNAME");
        } else if (env.containsKey("HOSTNAME")) {
            return env.get("HOSTNAME");
        } else {
            return "Unknown Computer";
        }
    }

    private static boolean validate(String code) {
        if (code == null || code.length() != 13) {
            throw NOT_VALID_EAN_EXCEPTION;
        }
        if (!CharMatcher.DIGIT.matchesAllOf(code)) {
            throw NOT_VALID_EAN_EXCEPTION;
        }
        String codeWithoutVd = code.substring(0, 12);
        int pretendVd = Integer.valueOf(code.substring(12, 13));
        int e = sumEven(codeWithoutVd);
        int o = sumOdd(codeWithoutVd);
        int me = o * 3;
        int s = me + e;
        int dv = getEanVd(s);
        if (!(pretendVd == dv)) {
            throw NOT_VALID_EAN_EXCEPTION;
        }

        return true;
    }

    private static int getEanVd(int s) {
        return 10 - (s % 10);
    }

    private static int sumEven(String code) {
        int sum = 0;
        for (int i = 0; i < code.length(); i++) {
            if (isEven(i)) {
                sum += Character.getNumericValue(code.charAt(i));
            }
        }
        return sum;
    }

    private static int sumOdd(String code) {
        int sum = 0;
        for (int i = 0; i < code.length(); i++) {
            if (!isEven(i)) {
                sum += Character.getNumericValue(code.charAt(i));
            }
        }
        return sum;
    }

    private static boolean isEven(int i) {
        return i % 2 == 0;
    }

    public static void checkEmail() throws NoSuchProviderException, MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties());
        Store store = session.getStore("imaps");
        store.connect("imap.googlemail.com", 993, "smallwolk1989@gmail.com", "tlsvdhbkhn");
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Fetch unseen messages from inbox folder
        Message[] messages = inbox.search(
                new FlagTerm(new Flags(Flags.Flag.SEEN), false));

        // Sort messages from recent to oldest
        Arrays.sort(messages, (m1, m2) -> {
            try {
                return m2.getSentDate().compareTo(m1.getSentDate());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        for (Message message : messages) {
//            message.getHeader("From");
            Address[] addresses = message.getFrom();
            for (Address address : addresses) {
                System.out.println("" + address.toString());
            }
//            String[] header = message.getHeader("From");
//            
//            for(String s : header) {
//                System.out.println("" + s);
//            }

//            message.getContent();
            System.out.println("");
            System.out.println("" + getTextFromMessage(message));

//            System.out.println("" + message.getHeader("From"));
//            System.out.println(
//                    "sendDate: " + message.getSentDate()
//                    + " subject:" + message.getSubject()
//                    + " subject:" + message.get
//            );
        }
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    public static int countReload;

    public static void getDescription() {
//        AliexApiCall.getProductDescription("32823781390", new CallApiInterface() {
//            @Override
//            public void onSuccess(String response) {
//                AliexProductDescriptionRes aliexProductDetail = AliexParseUtil.parseProductDesResponse(response);
//
//                if (aliexProductDetail == null) {
//                    AliexErrorResponse aliexErrorResponse = AliexParseUtil.parseErrorResponse(response);
//                    if (aliexErrorResponse != null) {
//                        System.out.println("" + aliexErrorResponse.get(0).getCode());
//                    }
//                    if (countReload == 0) {
//                        AliexParameterFactory.changeKey();
//                        countReload++;
//                        getDescription();
//                    }
//                } else {
////                    System.out.println("" + aliexProductDetail.getDescription());
//
//                    System.out.println("original: ");
//                    System.out.println("" + aliexProductDetail.getDescription());
//
//                    System.out.println("====================");
//
//                    String html = MarketUtil.removeImgSrc(aliexProductDetail.getDescription(), null);
//                    System.out.println("" + html);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Exception ex) {
//
//            }
//        });
    }

    public static boolean checkIsUPC(long input) {

        long number = input;

        // determine whether number is a possible upc code	
        if ((input < MIN_POSSIBLE_UPC_CODE) || (input > MAX_POSSIBLE_UPC_CODE)) {
            // not a upc code	
//            System.out.println(input + " is an invalid UPC code");
            return false;
        } else {
            // might be a upc code	

            // determine individual digits	
            int d12 = (int) (number % 10);
            number /= 10;
            int d11 = (int) (number % 10);
            number /= 10;
            int d10 = (int) (number % 10);
            number /= 10;
            int d9 = (int) (number % 10);
            number /= 10;
            int d8 = (int) (number % 10);
            number /= 10;
            int d7 = (int) (number % 10);
            number /= 10;
            int d6 = (int) (number % 10);
            number /= 10;
            int d5 = (int) (number % 10);
            number /= 10;
            int d4 = (int) (number % 10);
            number /= 10;
            int d3 = (int) (number % 10);
            number /= 10;
            int d2 = (int) (number % 10);
            number /= 10;
            int d1 = (int) (number % 10);
            number /= 10;

            // compute sums of first 5 even digits and the odd digits	
            int m = d2 + d4 + d6 + d8 + d10;
            int n = d1 + d3 + d5 + d7 + d9 + d11;

            System.out.println("" + (m + 3 * n + d12));

            // use UPC formula to determine required value for d12	
//            int r = 10 - ((m + 3 * n) % 10);
            int r = (10 - ((m + 3 * n) % 10)) % 10;

            // based on r, can test whether number is a UPC code	
            if (r == d12) {
                // is a upc code	
//                System.out.println(input + " is a feasible UPC code");

                return true;
            } else {
                // not a upc code	
//                System.out.println(input + " is an invalid UPC code");

                return false;
            }
        }
    }

}
