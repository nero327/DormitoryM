package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Student;
import com.neepa.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT s.*,d.*,d.id as dormitory_id,b.name as building_name\n" +
            "FROM student s\n" +
            "LEFT JOIN dormitory d ON d.id = s.dormitory_id\n" +
            "LEFT JOIN building b ON b.id = d.building_id\n")
    List<StudentVO> selectStudentVoList();

    @Select("SELECT s.*,d.*,d.id as dormitory_id,b.name as building_name\n" +
            "FROM student s\n" +
            "LEFT JOIN dormitory d ON d.id = s.dormitory_id\n" +
            "LEFT JOIN building b ON b.id = d.building_id\n")
    List<StudentVO> selectStudentVoListByPage(IPage<StudentVO> page);

    @Select("select s.*,d.*,d.id as dormitory_id,b.name as building_name from student s, dormitory d, building b where b.id=d.building_id and d.id=s.dormitory_id and s.id=#{id}")
    StudentVO selectStudentVoById(@Param("id")String id);

    @Select("select s.* from student s where s.id=#{id}")
    StudentVO selectStudentById(@Param("id")String id);

    List<StudentVO> selectVoListByCondition(IPage<StudentVO> page, @Param("id") String id, @Param("name") String name, @Param("sex") Integer sex, @Param("phone") String phone);
}
