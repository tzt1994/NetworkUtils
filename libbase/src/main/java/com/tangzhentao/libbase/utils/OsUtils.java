package com.tangzhentao.libbase.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Description:手机系统工具类
 *
 * @author tangzhentao
 * @date 2019/5/29
 */
public class OsUtils {

    //MIUI标识
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    //EMUI标识
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    //Flyme标识
    private static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    private static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    private static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    private static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";

    /**
     * 是否魅族Flyme系统
     * @return boolean
     */
    public static boolean isFlyme() {
        if (isPropertyExist(KEY_FLYME_ICON_FALG, KEY_FLYME_SETUP_FALG, KEY_FLYME_PUBLISH_FALG)) {
            return true;
        }

        try {
            BuildProperties properties = BuildProperties.instance();
            if (properties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                String romName = properties.getProperty(KEY_FLYME_ID_FALG_KEY);
                if (!TextUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否是EMUI系统
     * @return boolean
     */
    public static boolean isEMUI() {
        return isPropertyExist(KEY_EMUI_API_LEVEL, KEY_EMUI_CONFIG_HW_SYS_VERSION, KEY_EMUI_VERSION_CODE);
    }

    /**
     * 是否是MIUI系统
     * @return boolean
     */
    public static boolean isMIUI() {
        return isPropertyExist(KEY_MIUI_INTERNAL_STORAGE, KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME);
    }

    private static boolean isPropertyExist(String... keys) {
        if (keys == null || keys.length == 0) {
            return false;
        }

        try {
            BuildProperties properties = BuildProperties.instance();
            for (String key : keys) {
                String value = properties.getProperty(key);
                if (!TextUtils.isEmpty(value)) {
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static final class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            //读取系统配置信息
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public boolean containsKey(Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(String name) {
            return properties.getProperty(name);
        }

        public String getProperty(String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties instance() throws  IOException{
            return new BuildProperties();
        }
    }
}
