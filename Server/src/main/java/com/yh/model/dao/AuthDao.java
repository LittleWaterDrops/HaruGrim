package com.yh.model.dao;

import com.yh.model.dto.Auth;
import java.util.Date;

public interface AuthDao {
    void insertUser(Auth auth); // 회원가입

    Auth findUserByUserEmail(String username); // 회원탈퇴시 회원 조회용
}
