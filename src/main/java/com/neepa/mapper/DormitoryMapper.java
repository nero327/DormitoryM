package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Dormitory;
import com.neepa.vo.DormitoryVO;
import com.neepa.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DormitoryMapper extends BaseMapper<Dormitory> {
    @Select("select d.*,b.name as building_name from dormitory d, building b where b.id=d.building_id")
    List<DormitoryVO> selectVoList();

    @Select("select d.*,b.name as building_name from dormitory d, building b where b.id=d.building_id")
    List<DormitoryVO> selectVoListByPage(IPage<DormitoryVO> page);

    @Select("select d.* from dormitory d where d.building_id=#{buildingId} and d.floor=#{floor} and d.number=#{number}")
    Dormitory queryDormitoryByInfo(@Param("buildingId") Integer buildingId,
                                   @Param("floor") Integer floor,
                                   @Param("number") Integer number);

    @Select("SELECT s.*,d.*,d.id as dormitory_id,b.name as building_name\n" +
            "FROM student s\n" +
            "LEFT JOIN dormitory d ON d.id = s.dormitory_id\n" +
            "LEFT JOIN building b ON b.id = d.building_id\n" +
            "WHERE d.id = #{id}")
    List<StudentVO> selectStudentListById(@Param("id") Integer id);

    List<StudentVO> selectVoListByCondition(IPage<DormitoryVO> dormitoryVOPage,
                                            @Param("buildingId") Integer buildingId,
                                            @Param("floor") Integer floor,
                                            @Param("number") Integer number);
}
