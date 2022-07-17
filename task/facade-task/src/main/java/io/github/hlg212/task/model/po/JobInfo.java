package io.github.hlg212.task.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import lombok.Data;

import java.util.Date;


@Data
public class JobInfo extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="任务ID")
	private String id;

	@Field(description="执行器id")
	private String zxqid;

	@Field(description="任务描述")
	private String rwms;

	@Field(description="路由策略")
	private String lycl;

	@Field(description="cron表达式")
	private String cron;

	@Field(description="运行模式")
	private String yxms;

	@Field(description="执行器任务handler")
	private String handler;

	@Field(description="阻塞处理策略")
	private String zsclcl;

	@Field(description="子任务id")
	private String zrwid;

	@Field(description="超时时间")
	private String cssj;

	@Field(description="失败重试次数")
	private String sbcscs;

	@Field(description="负责人")
	private String cjr;

	@Field(description = "邮件")
	private String email;

	@Field(description="系统名")
	private String xtm;

	@Field(description="类名")
	private String lm;

	@Field(description="参数")
	private String param;

	@Field(description="状态")
	private String status;
}
