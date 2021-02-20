package he.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.EduTeacher;
import he.edu.eduservice.service.EduTeacherService;
import he.edu.eduservice.vo.TeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * 刪除与恢复
     *
     * @param id 主鍵
     * @return boolean
     */
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("/delete")
    public ResultEntity delete(@ApiParam(name = "id", value = "主键", required = true) String id, @ApiParam(name = "status", value = "是否删除") Integer status) {
        boolean flag;
        if (status.equals(0)) {
            EduTeacher teacher = new EduTeacher();
            teacher.setId(id);
            teacher.setIsDeleted(false);
            flag = eduTeacherService.updateById(teacher);
        } else {
            flag = eduTeacherService.removeById(id);
        }
        if (!flag) {
            return ResultEntity.error();
        }

        return ResultEntity.ok();
    }

    /**
     * @return
     * @paam 参数
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/index")
    public ResultEntity index(@RequestParam Map<String, Object> map) {
        Integer page = 0;
        Integer size = 20;
        try {
            page = (Integer.parseInt((String) map.get("page")));
            size = Integer.parseInt((String) map.get("size"));
        } catch (NumberFormatException e) {
            System.out.println("number：" + e.getMessage());
        }

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //条件构造器
        Page<EduTeacher> build = new Page<>(page, size);
        eduTeacherService.page(build, wrapper);
        List<EduTeacher> records = build.getRecords();

        return ResultEntity.ok().page(build.getTotal(), build.getSize(), build.getCurrent()).data("items", records);
    }

    /**
     * @return
     * @paam 参数
     */
    @ApiOperation(value = "查询讲师列表")
    @GetMapping("/all")
    public ResultEntity all() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return ResultEntity.ok().page(new Long(list.size()), 0L, 0L).data("items", list);
    }

    /**
     * @return
     * @paam 参数
     */
    @ApiOperation(value = "条件查询讲师列表")
    @PostMapping("/query")
    public ResultEntity query(@RequestBody TeacherVo vo, Long page, Long size) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getLevel())) {
            wrapper.eq("level", vo.getLevel());
        }
        if (!StringUtils.isEmpty(vo.getName())) {
            wrapper.like("name", vo.getName());
        }
        if (!StringUtils.isEmpty(vo.getBegin())) {
            wrapper.ge("gmt_create", vo.getBegin());
        }
        if (!StringUtils.isEmpty(vo.getEnd())) {
            wrapper.le("gmt_create", vo.getEnd());
        }

        if (StringUtils.isEmpty(page)) {
            page = 0L;
        }
        if (StringUtils.isEmpty(size)) {
            size = 20L;
            //条件构造器
        }
        Page<EduTeacher> build = new Page<>(page, size);
        eduTeacherService.page(build, wrapper);
        List<EduTeacher> records = build.getRecords();

        List<EduTeacher> list = eduTeacherService.list(wrapper);
        return ResultEntity.ok().page(build.getTotal(), build.getSize(), build.getCurrent()).data("items", list);
    }

    /**
     * @return
     * @paam 参数
     */
    @ApiOperation(value = "新增讲师列表")
    @PostMapping("/add")
    public ResultEntity query(@RequestBody EduTeacher teacher) {
        boolean save = eduTeacherService.save(teacher);
        if (save) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }

    }

    /**
     * @return
     * @paam 参数
     */
    @ApiOperation(value = "根据id查询讲师列表")
    @GetMapping("/detail")
    public ResultEntity detail(@RequestParam Integer id) {
        EduTeacher ed = eduTeacherService.getById(id);
        if (!StringUtils.isEmpty(ed)) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }

    /**
     * @return
     * @paam 参数
     */
    @ApiOperation(value = "根据id修改讲师列表")
    @PutMapping("/update")
    public ResultEntity update(@RequestBody EduTeacher teacher) {
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new HeException(20002, "计算错误!!.");
        }

        boolean flag = eduTeacherService.updateById(teacher);
        if (flag) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }


}