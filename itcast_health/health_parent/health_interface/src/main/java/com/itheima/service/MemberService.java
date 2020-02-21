package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {
    //根据手机号查询会员
    Member findByTelephone(String  telephone);
    void add(Member member);

    List<Integer> findMemberCountByMonths(List<String> months);
}
