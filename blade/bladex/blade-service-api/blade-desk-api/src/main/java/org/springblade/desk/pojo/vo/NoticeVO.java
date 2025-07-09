package org.springblade.desk.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.desk.pojo.entity.Notice;

/**
 * 通知公告视图类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends Notice {

	@Schema(description = "通知类型名")
	private String categoryName;

	@Schema(description = "租户编号")
	private String tenantId;

}
