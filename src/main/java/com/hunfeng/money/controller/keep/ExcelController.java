package com.hunfeng.money.controller.keep;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hunfeng.money.common.DemoException;
import com.hunfeng.money.common.Result;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.ExcelBillData;
import com.hunfeng.money.listener.ExcelBillDataListener;
import com.hunfeng.money.service.BillService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/money/excel")
public class ExcelController {

    @Autowired
    private BillService billService;
    @ApiOperation(value = "Excel批量导入数据")
    @PostMapping("import/{userId}")
    public Result batchImport(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("userId") Integer userId,
            @ApiParam(value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file)
            throws DemoException {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            List<Bill> bills = billService.batchImport(inputStream, userId);
            return Result.success("批量导入成功", bills);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DemoException("Excel数据导入错误");
        }
    }

    @ApiOperation(value = "支付宝账单Excel批量导入数据")
    @PostMapping("alipay/import/{userId}")
    public Result alipayBatchImport(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("userId") Integer userId,
            @ApiParam(value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file)
            throws DemoException {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            List<Bill> bills = billService.alipayBatchImport(inputStream, userId);
            return Result.success("批量导入成功", bills);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DemoException("Excel数据导入错误");
        }
    }
}
