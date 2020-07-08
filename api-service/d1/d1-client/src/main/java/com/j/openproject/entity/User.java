package com.j.openproject.entity;


import java.util.Date;

import lombok.Data;

@Data
public class User {


    private Integer id;


    private Integer companyId;


    private String userName;


    private String password;


    private String name;


    private String headIcon;


    private String mobile;


    private String telephone;


    private String gender;


    private String email;


    private Integer departmentId;


    private String job;


    private String jobNumber;


    private Integer bindMobile;


    private Integer bindEmail;

    private Integer verify;


    private Date createDatetime;


    private Date updateDatetime;


    private Date lastPasswordErrorDate;


    private Integer passwordErrorRetryTimes;


    private String blockChainAddress;


    private Date latestPasswordUpdateTime;










}