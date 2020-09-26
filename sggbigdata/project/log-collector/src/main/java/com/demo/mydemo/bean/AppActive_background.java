package com.demo.mydemo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户后台活跃
 */
@Setter
@Getter
public class AppActive_background {
    private String active_source;//1=upgrade,2=download(下载),3=plugin_upgrade
}
