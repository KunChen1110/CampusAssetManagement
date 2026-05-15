package com.campus.asset.controller.admin;

import com.campus.asset.constant.JwtClaimsConstant;
import com.campus.asset.dto.EmployeeDTO;
import com.campus.asset.dto.EmployeeLoginDTO;
import com.campus.asset.dto.EmployeePageQueryDTO;
import com.campus.asset.entity.Employee;
import com.campus.asset.properties.JwtProperties;
import com.campus.asset.result.PageResult;
import com.campus.asset.result.Result;
import com.campus.asset.service.EmployeeService;
import com.campus.asset.utils.JwtUtil;
import com.campus.asset.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登陆")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @ApiOperation(value = "新增员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();
    }

    @ApiOperation(value = "员工分页查询")
    @GetMapping("/page")
    public Result<PageResult<Employee>> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询，参数为: {}",employeePageQueryDTO);
        PageResult<Employee> pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用禁用员工账号：{},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询员工信息")
    public  Result<Employee> getById(@PathVariable long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation(value = "编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
