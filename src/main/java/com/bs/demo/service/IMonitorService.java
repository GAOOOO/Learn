package com.bs.demo.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IMonitorService {
    Map<String,Object> getServers(HttpServletRequest request);
}
