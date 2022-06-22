package com.neepa;

import com.neepa.entity.Permission;
import com.neepa.mapper.PermissionMapper;
import com.neepa.mapper.RepairMapper;
import com.neepa.vo.RepairVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DmsApplicationTests {
    @Autowired
    PermissionMapper permissionMapper;
    @Test
    void test1() {
        for (Permission permission : permissionMapper.selectPermissionList(null, null)) {
            System.out.println(permission);
        }
    }
}
