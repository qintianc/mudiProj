package com.mudi.demo.data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mudi.demo.model.MyUser;

@Mapper
public interface MyUserMapper {

	@Results({
	    @Result(property = "name", column = "NAME"),
	    @Result(property = "age", column = "AGE"),
	    @Result(property = "id", column = "ID"),
	    @Result(property = "role", column = "ROLE"),
	    @Result(property = "userName", column = "USERNAME"),
	    @Result(property = "password", column = "PASSWORD")
	})
	@Select("select * from T_USER WHERE USERNAME = '${userName}'")
	public MyUser getUser(@Param(value="userName") String userName);
	
	@Select("select * from T_USER")
	public List<Map<String,Object>> getList();
}
