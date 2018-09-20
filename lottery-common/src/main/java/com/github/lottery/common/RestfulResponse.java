/*
 * Copyright 2016 mopote.com
 *
 * The Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.github.lottery.common;

import java.io.Serializable;
import java.util.List;

public class RestfulResponse implements Serializable {

    private static final long serialVersionUID = 5209529094956651022L;

    private boolean success;

    private String code;

    private String msg;
    /**
     * total recorded
     */
    private int results;
    /**
     * rows
     */
    private List<?> rows;
    
    private Object single;

    public RestfulResponse() {
        super();
    }

    public RestfulResponse(boolean success) {
        super();
        this.success = success;
    }

    public RestfulResponse(boolean success, String code, String msg) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public RestfulResponse(boolean success, String code, String msg, int results, List<?> rows) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.results = results;
        this.rows = rows;
    }
    
    public RestfulResponse(boolean success, String code, String msg, Object single) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.single = single;
    }

    public RestfulResponse(boolean success, String code, String msg, Object single, int results, List<?> rows) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.single = single;
        this.results = results;
        this.rows = rows;
    }

    public static RestfulResponse SUCCESS() {
        return new RestfulResponse(true);
    }
    
    public static RestfulResponse FAILED(String message) {
        return new RestfulResponse(false, null, message);
    }
    
    public static RestfulResponse FAILED(String code, String message) {
        return new RestfulResponse(false, code, message);
    }

    public static RestfulResponse SUCCESS(int results, List<?> rows) {
        return new RestfulResponse(true, null, null, results, rows);
    }
    
    public static RestfulResponse SUCCESS(Object single) {
        return new RestfulResponse(true, null, null, single);
    }
    
    public static RestfulResponse SUCCESS(Object single, int results, List<?> rows) {
        return new RestfulResponse(true, null, null, single, results, rows);
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Object getSingle() {
        return single;
    }

    public void setSingle(Object single) {
        this.single = single;
    }
}
