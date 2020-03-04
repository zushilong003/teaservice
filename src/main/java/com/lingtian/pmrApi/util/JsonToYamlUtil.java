package com.lingtian.pmrApi.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.io.StringWriter;
import java.util.*;

/**
 * json toyaml
 */
public class JsonToYamlUtil {

    /**
     * json to properties
     *
     * @param object
     * @return
     */
    public static Properties jsonToProperties(Map<String, Object> object) {
        Properties prop = new Properties();
        for (String key : object.keySet()) {
            if (object.get(key) instanceof Integer) {
                prop.setProperty(key, ((Integer) object.get(key)).toString());
            } else if (object.get(key) instanceof Float) {
                prop.setProperty(key, ((Float) object.get(key)).toString());
            } else if (object.get(key) instanceof Double) {
                prop.setProperty(key, ((Double) object.get(key)).toString());
            } else if (object.get(key) instanceof String) {
                prop.setProperty(key, (String) object.get(key));
            } else if (object.get(key) instanceof Short) {
                prop.setProperty(key, ((Short) object.get(key)).toString());
            }
        }
        return prop;
    }

    /**
     * properties to yaml
     *
     * @param prop
     * @return
     */
    public static String propertiesToYaml(Properties prop) {
        StringWriter writer = new StringWriter();
        try {
            JavaPropsFactory factory = new JavaPropsFactory();
            JsonParser parser = factory.createParser(prop);

            YAMLFactory yamlFactory = new YAMLFactory();
            YAMLGenerator generator = yamlFactory.createGenerator(writer);
            JsonToken token = parser.nextToken();

            while (token != null) {
                if (JsonToken.START_OBJECT.equals(token)) {
                    generator.writeStartObject();
                } else if (JsonToken.FIELD_NAME.equals(token)) {
                    generator.writeFieldName(parser.getCurrentName());
                } else if (JsonToken.VALUE_STRING.equals(token)) {
                    generator.writeString(parser.getText());
                } else if (JsonToken.END_OBJECT.equals(token)) {
                    generator.writeEndObject();
                }
                token = parser.nextToken();
            }
            parser.close();
            generator.flush();
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return writer.toString();
    }

    /**
     * json to yaml
     *
     * @param json
     * @return
     */
    public static String jsonToYaml(String json) {
        Map<String, Object> object = JSON.parseObject(json, Map.class);
        Properties properties = jsonToProperties(object);
        String response = propertiesToYaml(properties);
        return response;
    }

    /**
     * json to yaml
     *
     * @param yaml
     * @return
     */
    public static String yamlToJson(String yaml) {
        final String DOT = ".";
        Map propMap = new HashMap();
        try {
            YAMLFactory yamlFactory = new YAMLFactory();
            YAMLParser parser = yamlFactory.createParser(yaml);

            String key = "";
            String value = null;
            JsonToken token = parser.nextToken();
            while (token != null) {
                if (JsonToken.START_OBJECT.equals(token)) {
                    // do nothing
                } else if (JsonToken.FIELD_NAME.equals(token)) {
                    if (key.length() > 0) {
                        key = key + DOT;
                    }
                    key = key + parser.getCurrentName();

                    token = parser.nextToken();
                    if (JsonToken.START_OBJECT.equals(token)) {
                        continue;
                    }
                    value = parser.getText();
                    // lines.add(key + "=" + value);
                    propMap.put(key, value);

                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    }
                    value = null;
                } else if (JsonToken.END_OBJECT.equals(token)) {
                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    }
//                      else {
//                        key = "";
//                        lines.add("");
//                    }
                }

                token = parser.nextToken();
            }
            parser.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JSON.toJSONString(propMap);
    }


    public static void main(String args[]) {
        String json = "{\"spec.slaveImage\":\"harbor.paas.shein.io/k8s/testemr1-slave-master-default:2019.09.05-15.36.59\",\"spec.slavePvc\":\"1Gi\",\"spec.kind\":\"PmrService\",\"spec.masterImage\":\"harbor.paas.shein.io/k8s/testemr1-master-default:2019.09.05-15.35.55\",\"spec.resources.limits.memory\":\"4Gi\",\"spec.masterPvc\":\"1Gi\",\"spec.scn\":\"nfs-dynamic-class\",\"spec.resources.requests.cpu\":\"100m\",\"spec.size\":\"1\",\"spec.apiVersion\":\"app.shein.com/v1alpha1\",\"spec.resources.requests.memory\":\"200Mi\",\"spec.resources.limits.cpu\":\"4\",\"spec.hostTail\":\"abc-test.sheincorp.cn\",\"spec.nodeSelector.env\":\"dev\"}\n";
        System.out.println(jsonToYaml(json));

        String yaml = "spec:\n" +
                "  masterPvc: \"1Gi\"\n" +
                "  resources:\n" +
                "    limits:\n" +
                "      cpu: \"4\"\n" +
                "      memory: \"4Gi\"\n" +
                "    requests:\n" +
                "      memory: \"200Mi\"\n" +
                "      cpu: \"100m\"\n" +
                "  masterImage: \"harbor.paas.shein.io/k8s/testemr1-master-default:2019.09.05-15.35.55\"\n" +
                "  scn: \"nfs-dynamic-class\"\n" +
                "  slavePvc: \"1Gi\"\n" +
                "  nodeSelector:\n" +
                "    env: \"dev\"\n" +
                "  slaveImage: \"harbor.paas.shein.io/k8s/testemr1-slave-master-default:2019.09.05-15.36.59\"\n" +
                "  hostTail: \"abc-test.sheincorp.cn\"\n" +
                "  size: \"1\"\n" +
                "kind: \"PmrService\"\n" +
                "apiVersion: \"app.shein.com/v1alpha1\"";
        System.out.println(yamlToJson(yaml));

    }
}
