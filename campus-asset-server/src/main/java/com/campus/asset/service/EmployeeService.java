package com.campus.asset.service;

import com.campus.asset.dto.EmployeeDTO;
import com.campus.asset.dto.EmployeeLoginDTO;
import com.campus.asset.dto.EmployeePageQueryDTO;
import com.campus.asset.entity.Employee;
import com.campus.asset.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Employee getById(long id);


    /**
     * 修改员工信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
