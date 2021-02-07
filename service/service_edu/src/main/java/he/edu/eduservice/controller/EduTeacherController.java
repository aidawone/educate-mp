package he.edu.eduservice.controller;


import he.edu.eduservice.entity.EduTeacher;
import he.edu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-07
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    final EduTeacherService eduTeacherService;

    public EduTeacherController(EduTeacherService eduTeacherService) {
        this.eduTeacherService = eduTeacherService;
    }

    /**
     * 查詢所有
     *
     * @return List<EduTeacher>
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public List<EduTeacher> findAll() {
        //調用server的查詢方法
        List<EduTeacher> list = eduTeacherService.list(null);
        return list;
    }

    /**
     * 刪除
     *
     * @param id 主鍵
     * @return boolean
     */
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public boolean delete(@ApiParam(name = "id", value = "主键", required = true) @PathVariable String id) {
        return eduTeacherService.removeById(id);

    }
}

