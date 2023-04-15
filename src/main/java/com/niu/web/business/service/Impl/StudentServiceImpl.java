package com.niu.web.business.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.web.business.mapper.StudentDao;
import com.niu.web.business.pojo.StudentEntity;
import com.niu.web.business.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.Map;



@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentEntity> implements StudentService {



}