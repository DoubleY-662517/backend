package com.homeworkai.service;

import com.homeworkai.dto.LoginDTO;
import com.homeworkai.dto.RegisterDTO;
import com.homeworkai.vo.LoginVO;
import com.homeworkai.vo.UserVO;

public interface UserService {
    
    LoginVO login(LoginDTO dto);
    
    UserVO register(RegisterDTO dto);
    
    UserVO getCurrentUser();
    
    UserVO getUserById(String id);
}
