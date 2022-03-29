package com.bs.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.bs.demo.service.IMonitorService;
import com.bs.demo.utils.ServerUtils;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MonitorServiceImpl implements IMonitorService {

    @Override
    public Map<String, Object> getServers(HttpServletRequest request) {
        Map<String, Object> resultMap = new LinkedHashMap<>(8);
        try {
            SystemInfo si = new SystemInfo();
            OperatingSystem os = si.getOperatingSystem();
            HardwareAbstractionLayer hal = si.getHardware();
            // 请求信息
            resultMap.put("request", ServerUtils.getRequestInfo(request));
            // 系统信息
            resultMap.put("sys", ServerUtils.getSystemInfo(os));
            // cpu 信息
            resultMap.put("cpu", ServerUtils.getCpuInfo(hal.getProcessor()));
            // 内存信息
            resultMap.put("memory", ServerUtils.getMemoryInfo(hal.getMemory()));
            // 交换区信息
            resultMap.put("swap", ServerUtils.getSwapInfo(hal.getMemory()));
            // 磁盘
            resultMap.put("disk", ServerUtils.getDiskInfo(os));
            resultMap.put("time", DateUtil.format(new Date(), "HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }



}
