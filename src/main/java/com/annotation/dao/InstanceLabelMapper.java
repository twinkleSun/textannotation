package com.annotation.dao;

import com.annotation.model.InstaLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstanceLabelMapper {
    int insertInstanceLabel(InstaLabel instanceLabel);
}