package he.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/back/teacher")
@CrossOrigin
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
    public ResultEntity delete(@ApiParam(name = "id", value = "主键", required = true) String id) {
        boolean flag = eduTeacherService.removeById(id);
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
    public ResultEntity index(@ApiParam(name = "page", value = "当前页") Long page, @ApiParam(name = "size", value = "记录数") Long size) {
        if (StringUtils.isEmpty(page)) {
            page = 0L;
        }
        if (StringUtils.isEmpty(size)) {
            size = 20L;
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
    @GetMapping(value = "/query")
    public ResultEntity query(TeacherVo vo) {

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

        if (StringUtils.isEmpty(vo.getPage())) {
            vo.setPage(0L);
        }
        if (StringUtils.isEmpty(vo.getSize())) {
            vo.setSize(20L);
            //条件构造器
        }
        wrapper.orderByDesc("sort", "gmt_modified");
        Page<EduTeacher> build = new Page<>(vo.getPage(), vo.getSize());
        eduTeacherService.page(build, wrapper);
        List<EduTeacher> records = build.getRecords();

        return ResultEntity.ok().page(build.getTotal(), build.getSize(), build.getCurrent()).data("items", records);
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
    public ResultEntity detail(String id) {
        EduTeacher ed = eduTeacherService.getById(id);
        if (!StringUtils.isEmpty(ed)) {
            return ResultEntity.ok().data("item", ed);
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

        boolean flag = eduTeacherService.updateById(teacher);
        if (flag) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }


}