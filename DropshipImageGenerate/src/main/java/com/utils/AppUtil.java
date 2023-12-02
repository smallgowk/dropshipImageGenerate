/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

/**
 *
 * @author PhanDuy
 */
public class AppUtil {

//    private static String lastestAppVersion = "20200412";
    private static String appVersion = "20231028.1525";
//    private static String appVersion = "20220314.1720";
//    private static String appVersion = "20220312.0800";
//    private static String appVersion;

    public static String getAppVersion() {
        if (appVersion != null) {
            return appVersion;
        }

//        URLClassLoader cl = (URLClassLoader) AppUtil.class.getClassLoader();
//        URL url = cl.findResource("META-INF/MANIFEST.MF");
//        Manifest manifest;
//        try {
//            manifest = new Manifest(url.openStream());
//            Attributes attr = manifest.getMainAttributes();
//            
//            System.out.println("ok: " + manifest.getMainAttributes().getValue("Implementation-Title"));
//        } catch (IOException ex) {
//            Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        MavenXpp3Reader reader = new MavenXpp3Reader();
//        Model model = null;
////        if ((new File(System.getProperty("user.dir") + Configs.pathChar + "pom.xml")).exists()) {
//        try {
//            File file = new File(System.getProperty("user.dir") + Configs.pathChar + "pom.xml");
//            if (file.exists()) {
//                model = reader.read(new FileReader(System.getProperty("user.dir") + Configs.pathChar + "pom.xml"));
//                appVersion = model.getVersion();
//                System.out.println("" + appVersion);
//            } else {
//                System.out.println("File not found");
//            }
////                System.out.println("model: " + model.getVersion());
////                if(model != null && model.getVersion() != null) {
////                    appVersion = model.getVersion();
////                }
////                
////                return appVersion;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException | XmlPullParserException ex) {
//            Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (appVersion != null && !appVersion.isEmpty()) {
//                return appVersion;
//            } else {
////                appVersion = lastestAppVersion;
//                return "";
//            }
//        }
//        }
        return null;
    }

//    public static String getAppVersion() {
//        MavenXpp3Reader reader = new MavenXpp3Reader();
//        Model model = null;
//        if ((new File(System.getProperty("user.dir") + Configs.pathChar +  "pom.xml")).exists()) {
//            try {
//                model = reader.read(new FileReader(System.getProperty("user.dir") + Configs.pathChar +  "pom.xml"));
//                System.out.println("model: " + model.getVersion());
////                if(model != null && model.getVersion() != null) {
////                    appVersion = model.getVersion();
////                }
////                
////                return appVersion;
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException | XmlPullParserException ex) {
//                Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
