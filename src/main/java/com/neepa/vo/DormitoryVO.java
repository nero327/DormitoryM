package com.neepa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DormitoryVO {
    private Integer id;
    private Integer floor;
    private Integer number;
    private Integer available;
    private Integer max;
    private Integer buildingId;
    private String buildingName;

}
