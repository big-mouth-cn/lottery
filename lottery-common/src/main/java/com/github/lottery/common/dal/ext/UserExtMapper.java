package com.github.lottery.common.dal.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserExtMapper {

    int decrement(@Param("id") Long id, @Param("value") Integer value);

    int increment(@Param("id") Long id, @Param("value") Integer value, @Param("gain") int gain);
}
