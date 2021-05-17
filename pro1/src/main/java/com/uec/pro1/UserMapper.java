package com.uec.pro1;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    void insert(UserPo userPo);
    UserPo select(String username);
    void update(UserPo userPo);
    void delete(String username);
}
