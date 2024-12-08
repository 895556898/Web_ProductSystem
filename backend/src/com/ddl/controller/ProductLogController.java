package com.ddl.controller;

import com.ddl.entity.dto.PageParamDTO;
import com.ddl.service.ProductLogService;
import io.javalin.http.Context;

public class ProductLogController {
    private final static ProductLogService productLogService = ProductLogService.getInstance();

    public static void listLog(Context ctx) {
        ctx.json(productLogService.listLog(ctx.bodyAsClass(PageParamDTO.class)));
    }
}
