package com.dtdream.DtRecommender.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by handou on 8/24/16.
 */
public class ConfParser {
    private Properties p;
    private String module;

    public ConfParser(String module, String confFile) {
        this.p = new Properties();
        this.module = module;

        try {
            this.parse(new File(LogHelper.getAbsolutePath(this.module, confFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }

    private void parse(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);

        try {
            p.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return p.getProperty(key);
    }

    public String getString(String key, String def) {
        return p.getProperty(key, def);
    }

    public int getInt(String key) {
        return Integer.parseInt(p.getProperty(key));
    }

    public int getInt(String key, int def) {
        return Integer.parseInt(p.getProperty(key, String.format("%d", def)));
    }
}
