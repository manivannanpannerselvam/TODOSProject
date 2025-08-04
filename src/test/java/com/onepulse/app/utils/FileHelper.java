package com.onepulse.app.utils;
//import com.product.genericcomponents.config.Configvariable;
//import com.product.genericcomponents.exception.TapException;
//import com.product.genericcomponents.exception.TapExceptionType;
//import com.product.genericcomponents.filehandling.CsvUtils;
import com.prod.tap.config.Configvariable;
import com.prod.tap.exception.TapException;
import com.prod.tap.exception.TapExceptionType;
import com.prod.tap.filehandling.CsvUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class FileHelper {

    private static final Logger logger = Logger.getLogger(FileHelper.class);

    private Configvariable configvariable = new Configvariable();

    private CsvUtils csvUtils = new CsvUtils();

    public void loadLocalizationFile(String lbu, String language, String platform) {
        //  String filePath = "/pulse_localization/" + lbu + "/" + language + "_" + platform.toLowerCase() + ".csv";
        // loadCSVDataIntoGlobalMap(filePath, '=');
        InputStream initialStreamLocale;
        if(platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("iOS")) {
            if (language.equalsIgnoreCase("en")) {
                initialStreamLocale = getClass().getResourceAsStream("/localization/en_common.properties");
                configvariable.propertiesFileLoad(initialStreamLocale);
                initialStreamLocale = getClass().getResourceAsStream("/localization/" + lbu + "/" + language + "_text" + ".properties");
            } else {
                initialStreamLocale = getClass().getResourceAsStream("/localization/" + lbu + "/" + language + "_text" + ".properties");
            }
            configvariable.propertiesFileLoad(initialStreamLocale);
//            InputStream initialStreamLocator = getClass().getResourceAsStream("/mobilelocatorfile/" + platform.toLowerCase() + ".properties");
//            configvariable.propertiesFileLoad(initialStreamLocator);

            InputStream initialStreamLocator;
            initialStreamLocator = getClass().getResourceAsStream("/mobilelocatorfile/" + platform.toLowerCase() + ".properties");
            configvariable.propertiesFileLoad(initialStreamLocator);
            initialStreamLocator = getClass().getResourceAsStream("/mobilelocatorfile/" + lbu + "/" + platform.toLowerCase() + ".properties");
            configvariable.propertiesFileLoad(initialStreamLocator);


        }
        else if(platform.equalsIgnoreCase("web"))
     //   else if(platform.equalsIgnoreCase(System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME")))
        {
            if (language.equalsIgnoreCase("en")) {
                initialStreamLocale = getClass().getResourceAsStream("/localization/en_common.properties");
                configvariable.propertiesFileLoad(initialStreamLocale);
                initialStreamLocale = getClass().getResourceAsStream("/localization/" + lbu + "/" + language + "_text" + ".properties");
            } else {
                initialStreamLocale = getClass().getResourceAsStream("/localization/" + lbu + "/" + language + "_text" + ".properties");
            }
            configvariable.propertiesFileLoad(initialStreamLocale);
            InputStream initialStreamLocator = getClass().getResourceAsStream("/Weblocatorfile/" + platform.toLowerCase() + ".properties");
            configvariable.propertiesFileLoad(initialStreamLocator);

        }


    }

    public void loadCSVDataIntoGlobalMap(String fileName, char separator) {
        InputStream initialStream = getClass().getResourceAsStream(fileName);
        List<String[]> allCsvData = csvUtils.csvInputStreamReader(initialStream, separator);
        // print Data
        for (String[] row : allCsvData) {
            configvariable.setStringVariable(row[1], row[0]);
        }
    }

    public void setUpLocaleProperties(String language) {
        InputStream initialStream = null;
        if ("ios".equalsIgnoreCase(System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME"))) {
            initialStream = this.getClass().getResourceAsStream("/mobilelocalefile/ios_Locale.properties");
        } else if ("android".equalsIgnoreCase(System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME"))) {
            initialStream = this.getClass().getResourceAsStream("/mobilelocalefile/android_Locale.properties");

        }

        Properties localProp = propertiesFileLoad(initialStream);
        logger.info("loaded locale file");
        for (String key : localProp.stringPropertyNames()) {
            if (key.contains(language.toLowerCase() + ".")) {
                System.setProperty("pulse.country", localProp.getProperty(key));
                break;
            } else {
                System.setProperty("pulse.country", "GB");
            }
        }

    }

    public void loadLocatorProperties(String language, String lbu, String platform) {
        InputStream initialStream = null;
        URI uri = null;
        List<String> filenames = null;
        String fileName = "mobilelocatorfile/" + lbu.toLowerCase();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            uri = classLoader.getResource(fileName).toURI();
        } catch (Exception e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to get properties file from path: [{}]", fileName);
        }

        if (uri == null) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "something is wrong directory or files missing");
        }

        if (uri.getScheme().contains("jar")) {
            filenames = getFilesFromClassResourceFromJar(uri, fileName);
        } else {
            filenames = getFilesFromClassResource("/" + fileName);
        }

        logger.info(filenames.toString());
        for (String file : filenames) {
            if (file.toLowerCase().contains(platform.toLowerCase()) && file.toLowerCase().contains(language.toLowerCase() + "_")) {
                initialStream = getClass().getResourceAsStream("/" + fileName + "/" + file);
                break;
            }
        }
        propertiesFileLoad(initialStream);
        logger.info("loaded locator file");
    }

    /**
     * this method loads properties files config and file having test data.
     *
     * @param initialStream android or ios, to load specific test data file.
     */
    public Properties propertiesFileLoad(InputStream initialStream) {
        Properties prop = new Properties();
        try {
            prop.load(initialStream);
            for (String key : prop.stringPropertyNames()) {
                configvariable.envPropertyMap.put(key, prop.getProperty(key));
                configvariable.setStringVariable(prop.getProperty(key), key);
            }
        } catch (IOException e) {
            logger.error("Failed to load properties file :" + initialStream);
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to load properties file : [{}]", initialStream);
        }
        return prop;
    }

    public List<String> getFilesFromClassResource(String fileName) {
        List<String> filenames = new ArrayList<>();
        String resource;
        try {
            InputStream locatorFiles = this.getClass().getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(locatorFiles));
            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        } catch (Exception e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to get properties file from path: [{}]", fileName);

        }
        return filenames;
    }

    public List<String> getFilesFromClassResourceFromJar(URI uri, String folder) {
        List<String> filenames = new ArrayList<>();
        String resource;
        InputStream locatorFiles = null;
        logger.info("inside jar");
        try {
            URL jar = FileHelper.class.getProtectionDomain().getCodeSource().getLocation();
            //jar.toString() begins with file:
            //i want to trim it out...
            Path jarFile = Paths.get(jar.toString().substring("file:".length()));
            FileSystem fs = FileSystems.newFileSystem(jarFile, null);
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fs.getPath(folder));
            for (Path p : directoryStream) {
                filenames.add(p.getFileName().toString());
            }

        } catch (Exception e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to get properties file from path: [{}]", folder);
        }
        return filenames;
    }


    public void loadLanguageListFile() {
        Properties prop = new Properties();
        InputStream languageListStream = getClass().getResourceAsStream("/testdata/login/language_list.properties");
        try {
            prop.load(languageListStream);
            for (String key : prop.stringPropertyNames()) {
                configvariable.setStringVariable(prop.getProperty(key), key);
            }
        } catch (IOException e) {
            logger.error("Failed to load properties file :" + languageListStream);
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to load properties file : [{}]", languageListStream);
        }
    }


}