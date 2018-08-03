package com.mudi.demo.data;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DataTest {

	@Select("select NAME,AGE from T_USER WHERE ID = 1")
	public Map<String,Object> getInfo();
}
