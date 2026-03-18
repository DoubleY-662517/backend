package com.homeworkai.service.impl;

import com.homeworkai.dto.LoginDTO;
import com.homeworkai.dto.RegisterDTO;
import com.homeworkai.entity.SysUser;
import com.homeworkai.exception.BusinessException;
import com.homeworkai.repository.SysUserRepository;
import com.homeworkai.security.JwtUtils;
import com.homeworkai.service.UserService;
import com.homeworkai.vo.LoginVO;
import com.homeworkai.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final SysUserRepository sysUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginVO login(LoginDTO dto) {
        SysUser user = sysUserRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BusinessException("用户不存在"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        // 修改：将MongoDB ObjectId转换为Long
        Long userId = Long.valueOf(user.getId().hashCode());
        String token = jwtUtils.generateToken(userId, user.getUsername());

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUser(toUserVO(user));

        return vo;
    }


    @Override
    public UserVO register(RegisterDTO dto) {
        if (sysUserRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("用户名已存在");
        }
        
        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStatus(1);
        user.setDeleted(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        sysUserRepository.save(user);
        
        return toUserVO(user);
    }
    
    @Override
    public UserVO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            throw new BusinessException(401, "未登录");
        }
        
        String username = ((User) auth.getPrincipal()).getUsername();
        SysUser user = sysUserRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException("用户不存在"));
        
        return toUserVO(user);
    }
    
    @Override
    public String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            throw new BusinessException(401, "未登录");
        }
        
        String username = ((User) auth.getPrincipal()).getUsername();
        SysUser user = sysUserRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException("用户不存在"));
        
        return user.getId();
    }
    
    @Override
    public UserVO getUserById(String id) {
        SysUser user = sysUserRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在"));
        return toUserVO(user);
    }
    
    private UserVO toUserVO(SysUser user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}
