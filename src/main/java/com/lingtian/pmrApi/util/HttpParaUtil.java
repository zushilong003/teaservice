package com.lingtian.pmrApi.util;

import java.util.HashMap;
import java.util.Map;


public class HttpParaUtil extends HashMap<String, String> {
    public HttpParaUtil(String name, String namespace, String yaml) {
        super(4);
        this.put("name", name);
        this.put("namespace", namespace);
        this.put("info", yaml);
    }


    public HttpParaUtil(String name, String namespace) {
        super(4);
        this.put("name", name);
        this.put("namespace", namespace);

    }
}
